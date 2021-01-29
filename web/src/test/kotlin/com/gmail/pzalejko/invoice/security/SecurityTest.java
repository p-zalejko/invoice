package com.gmail.pzalejko.invoice.security;

import com.gmail.pzalejko.invoice.invoicerequest.infrastructure.DynamoDbResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(DynamoDbResource.class)
public class SecurityTest {

    public static final String API = "/api/v1/hello";

    @Inject
    SecurityRepositoryHelper repositoryHelper;

    @AfterEach
    public void setup() {
        repositoryHelper.setup();
    }

    @Test
    public void testValidUser() {
        repositoryHelper.createUser("foo", "bar".toCharArray(), 1, Set.of("USER"));
        given()
                .when()
                .auth()
                .basic("foo", "bar")
                .get(API)
                .then()
                .statusCode(200)
                .body(is("Hello foo"));
    }

    @Test
    public void testValidUserWithWrongPassword() {
        repositoryHelper.createUser("foo", "bar".toCharArray(), 1, Set.of("USER"));
        given()
                .when()
                .auth()
                .basic("foo", "123")
                .get(API)
                .then()
                .statusCode(401);
    }

    @Test
    public void testValidUserWithoutProperRole() {
        repositoryHelper.createUser("foo", "bar".toCharArray(), 1, Set.of("notExistingRole"));
        given()
                .when()
                .auth()
                .basic("foo", "bar")
                .get(API)
                .then()
                .statusCode(403);
    }
}