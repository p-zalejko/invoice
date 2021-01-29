package com.gmail.pzalejko.invoice.invoicerequest.web;

import com.gmail.pzalejko.invoice.invoicerequest.infrastructure.DynamoDbResource;
import com.gmail.pzalejko.invoice.invoicerequest.infrastructure.InvoiceRequestDatabaseRepository;
import com.gmail.pzalejko.invoice.security.SecurityRepositoryHelper;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(DynamoDbResource.class)
public class InvoiceRequestTest {

    public static final String API = "/api/v1/invoicerequest";
    public static final String USER_NAME = "foo";
    public static final String PASSWORD = "bar";

    @Inject
    DynamoDbClient dynamoDB;
    @Inject
    InvoiceRequestDatabaseRepository repository;

    @Inject
    SecurityRepositoryHelper repositoryHelper;

    @AfterEach
    public void setup() {
        if (dynamoDB.listTables().tableNames().contains(InvoiceRequestDatabaseRepository.TABLE_NAME)) {
            DeleteTableRequest request = DeleteTableRequest.builder()
                    .tableName(InvoiceRequestDatabaseRepository.TABLE_NAME)
                    .build();

            dynamoDB.deleteTable(request);
        }

        repository.init();
        repositoryHelper.setup();
        repositoryHelper.createUser(USER_NAME, PASSWORD.toCharArray(), 1, Set.of("USER"));
    }

    @Test
    public void createInvoiceRequest_unauthorized() {
        var now = LocalDate.now();

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(InvoiceRequestTestData.getInvoiceRequest(now))
                .post(API)
                .then()
                .statusCode(401);
    }

    @Test
    public void createInvoiceRequest() {
        var now = LocalDate.now();
        var expectedInvoiceNumber = String.format("1/%d/%d", now.getMonthValue(), now.getYear());

        verifyInvoice(now, expectedInvoiceNumber);
    }

    @Test
    public void createInvoiceRequest_dueDateFromThePast() {
        var now = LocalDate.now();
        var body = InvoiceRequestTestData.getInvoiceRequest(now.minusDays(1), now, now);
        verifyInvoice(body, 400);
    }

    @Test
    public void createInvoiceRequest_saleDateFromTheFuture() {
        var now = LocalDate.now();
        var body = InvoiceRequestTestData.getInvoiceRequest(now, now.plusMonths(1), now);
        verifyInvoice(body, 400);
    }

    @Test
    public void createManyInvoiceRequests_theSameMonth() {
        var now = LocalDate.now();
        for (int i = 1; i < 9; i++) {
            var expectedInvoiceNumber = String.format("%d/%d/%d", i, now.getMonthValue(), now.getYear());
            verifyInvoice(now, expectedInvoiceNumber);
        }
    }

    @Test
    public void createManyInvoiceRequests_differentMonths() {
        var now = LocalDate.now();
        // the current month
        for (int i = 1; i < 3; i++) {
            var expectedInvoiceNumber = String.format("%d/%d/%d", i, now.getMonthValue(), now.getYear());
            verifyInvoice(now, expectedInvoiceNumber);
        }

        // the next month
        now = now.plusMonths(1);
        for (int i = 1; i < 3; i++) {
            var expectedInvoiceNumber = String.format("%d/%d/%d", i, now.getMonthValue(), now.getYear());
            verifyInvoice(InvoiceRequestTestData.getInvoiceRequest(now, LocalDate.now(), now), expectedInvoiceNumber);
        }
    }

    private void verifyInvoice(LocalDate date, String expectedInvoiceNumber) {
        given()
                .when()
                .auth()
                .basic(USER_NAME, PASSWORD)
                .contentType(ContentType.JSON)
                .body(InvoiceRequestTestData.getInvoiceRequest(date))
                .post(API)
                .then()
                .statusCode(201)
                .body("invoiceNumber", is(expectedInvoiceNumber));
    }

    private void verifyInvoice(String body, String expectedInvoiceNumber) {
        given()
                .when()
                .auth()
                .basic(USER_NAME, PASSWORD)
                .contentType(ContentType.JSON)
                .body(body)
                .post(API)
                .then()
                .statusCode(201)
                .body("invoiceNumber", is(expectedInvoiceNumber));
    }

    private ExtractableResponse<Response> verifyInvoice(String body, int code) {
        return given()
                .when()
                .auth()
                .basic(USER_NAME, PASSWORD)
                .contentType(ContentType.JSON)
                .body(body)
                .post(API)
                .then()
                .statusCode(code)
                .extract();
    }
}