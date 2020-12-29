package com.gmail.pzalejko.invoice.invoicerequest.web;

import com.gmail.pzalejko.invoice.invoicerequest.infrastructure.DynamoDbResource;
import com.gmail.pzalejko.invoice.invoicerequest.infrastructure.MockInvoiceRequestRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(DynamoDbResource.class)
public class InvoiceRequestTest {

    public static final String API = "/api/v1/invoicerequest";

    @Inject
    MockInvoiceRequestRepository repository;

    @AfterEach
    public void setup() {
        repository.clear();
    }

    @Test
    public void testHelloEndpoint() {
        given()
                .when()
                .get(API)
                .then()
                .statusCode(200)
                .body(is("works!"));
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
        ExtractableResponse<Response> response = verifyInvoice(body, 400);
    }

    @Test
    public void createManyInvoiceRequests_theSameMonth() {
        var now = LocalDate.now();
        for (int i = 1; i < 9; i++) {
            var expectedInvoiceNumber = String.format("%d/%d/%d", i, now.getMonthValue(), now.getYear());
            verifyInvoice(now, expectedInvoiceNumber);
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