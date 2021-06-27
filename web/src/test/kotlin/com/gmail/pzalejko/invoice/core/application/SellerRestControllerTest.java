package com.gmail.pzalejko.invoice.core.application;

import com.gmail.pzalejko.invoice.invoicerequest.infrastructure.DynamoDbResource;
import com.gmail.pzalejko.invoice.invoicerequest.web.InvoiceRequestRepositoryHelper;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Set;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(DynamoDbResource.class)
public class SellerRestControllerTest {

    public static final String API = "/api/v1/sellers";
    public static final String USER_NAME = "foo";
    public static final String PASSWORD = "bar";

    @Inject
    SellerRepositoryHelper repositoryHelper;

    @BeforeEach
    public void setup() {
        repositoryHelper.setup();
        repositoryHelper.createUser(USER_NAME, PASSWORD.toCharArray(), 1, Set.of("USER"));
    }

    @AfterEach
    public void after() {
        repositoryHelper.clear();
    }

    @Test
    public void create_unauthorized() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(SellerTestData.getSeller(1))
                .post(API)
                .then()
                .statusCode(401);
    }

    @Test
    public void create() {
        given()
                .when()
                .auth()
                .basic(USER_NAME, PASSWORD)
                .contentType(ContentType.JSON)
                .body(SellerTestData.getSeller(1))
                .post(API)
                .then()
                .statusCode(201);
    }

    @Test
    public void create_preventFromDuplication() {
        String seller = SellerTestData.getSeller(1);
        given()
                .when()
                .auth()
                .basic(USER_NAME, PASSWORD)
                .contentType(ContentType.JSON)
                .body(seller)
                .post(API)
                .then()
                .statusCode(201);

        given()
                .when()
                .auth()
                .basic(USER_NAME, PASSWORD)
                .contentType(ContentType.JSON)
                .body(seller)
                .post(API)
                .then()
                .statusCode(400);
    }
}
