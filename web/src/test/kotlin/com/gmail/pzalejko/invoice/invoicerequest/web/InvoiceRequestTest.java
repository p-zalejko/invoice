package com.gmail.pzalejko.invoice.invoicerequest.web;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class InvoiceRequestTest {

    public static final String API = "/api/v1/invoicerequest";

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

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(InvoiceRequestTestData.getInvoiceRequest())
                .post(API)
                .then()
                .statusCode(201)
                .body("invoiceNumber", is(expectedInvoiceNumber));
    }
}