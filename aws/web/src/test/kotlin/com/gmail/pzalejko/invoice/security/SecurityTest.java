package com.gmail.pzalejko.invoice.security;

import com.gmail.pzalejko.invoice.invoicerequest.infrastructure.DynamoDbResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void setup() {
        repositoryHelper.setup();
    }

    @AfterEach
    public void after() {
        repositoryHelper.setup();
    }

    @Test
    public void testValidUser() {
        repositoryHelper.createUser("foo1", "bar".toCharArray(), 1, Set.of("USER"));
        given()
                .when()
                .auth()
                .basic("foo1", "bar")
                .get(API)
                .then()
                .statusCode(200)
                .body(is("Hello foo1"));
    }

    @Test
    public void testValidUserWithWrongPassword() {
        repositoryHelper.createUser("foo2", "bar".toCharArray(), 1, Set.of("USER"));
        given()
                .when()
                .auth()
                .basic("foo2", "123")
                .get(API)
                .then()
                .statusCode(401);
    }

    @Test
    public void testValidUserWithoutProperRole() {
        repositoryHelper.createUser("foo3", "bar".toCharArray(), 1, Set.of("notExistingRole"));
        given()
                .when()
                .auth()
                .basic("foo3", "bar")
                .get(API)
                .then()
                .statusCode(403);
    }
}