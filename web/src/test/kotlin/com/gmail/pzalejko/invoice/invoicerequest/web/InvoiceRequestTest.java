package com.gmail.pzalejko.invoice.invoicerequest.web;

import com.gmail.pzalejko.invoice.invoicerequest.infrastructure.DynamoDbResource;
import com.gmail.pzalejko.invoice.invoicerequest.infrastructure.InvoiceRequestDatabaseRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;

import javax.inject.Inject;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(DynamoDbResource.class)
public class InvoiceRequestTest {

    public static final String API = "/api/v1/invoicerequest";

    @Inject
    DynamoDbClient dynamoDB;
    @Inject
    InvoiceRequestDatabaseRepository repository;

    @AfterEach
    public void setup() {
        if (dynamoDB.listTables().tableNames().contains(InvoiceRequestDatabaseRepository.TABLE_NAME)) {
            DeleteTableRequest request = DeleteTableRequest.builder()
                    .tableName(InvoiceRequestDatabaseRepository.TABLE_NAME)
                    .build();

            dynamoDB.deleteTable(request);
        }

        repository.init();
    }

    @Test
    public void createInvoiceRequest_unauthorized() {
        var now = LocalDate.now();
        verifyInvoice(InvoiceRequestTestData.getInvoiceRequest(now), 401);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"USER"})
    public void createInvoiceRequest() {
        var now = LocalDate.now();
        var expectedInvoiceNumber = String.format("1/%d/%d", now.getMonthValue(), now.getYear());

        verifyInvoice(now, expectedInvoiceNumber);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"USER"})
    public void createInvoiceRequest_dueDateFromThePast() {
        var now = LocalDate.now();
        var body = InvoiceRequestTestData.getInvoiceRequest(now.minusDays(1), now, now);
        verifyInvoice(body, 400);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"USER"})
    public void createInvoiceRequest_saleDateFromTheFuture() {
        var now = LocalDate.now();
        var body = InvoiceRequestTestData.getInvoiceRequest(now, now.plusMonths(1), now);
        verifyInvoice(body, 400);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"USER"})
    public void createManyInvoiceRequests_theSameMonth() {
        var now = LocalDate.now();
        for (int i = 1; i < 9; i++) {
            var expectedInvoiceNumber = String.format("%d/%d/%d", i, now.getMonthValue(), now.getYear());
            verifyInvoice(now, expectedInvoiceNumber);
        }
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"USER"})
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
                .contentType(ContentType.JSON)
                .body(body)
                .post(API)
                .then()
                .statusCode(code)
                .extract();
    }
}