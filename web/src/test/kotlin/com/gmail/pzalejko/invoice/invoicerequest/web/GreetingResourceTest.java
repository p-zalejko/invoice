package com.gmail.pzalejko.invoice.invoicerequest.web;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test    
    public void testHelloEndpoint() {
        given()
          .when().get("/api/v1/invoicerequest")
          .then()
             .statusCode(200)    
             .body(is("works!"));
    }
}