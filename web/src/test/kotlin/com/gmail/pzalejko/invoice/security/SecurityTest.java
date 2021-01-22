package com.gmail.pzalejko.invoice.security;

import com.gmail.pzalejko.invoice.invoicerequest.infrastructure.DynamoDbResource;
import com.gmail.pzalejko.invoice.security.infrastructure.DynamoDbUserRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;

import javax.inject.Inject;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(DynamoDbResource.class)
public class SecurityTest {

    public static final String API = "/api/v1/hello";

    @Inject
    DynamoDbClient dynamoDB;
    @Inject
    DynamoDbUserRepository userRepository;

    @AfterEach
    public void setup() {
        if (dynamoDB.listTables().tableNames().contains(DynamoDbUserRepository.TABLE_NAME)) {
            DeleteTableRequest request = DeleteTableRequest.builder()
                    .tableName(DynamoDbUserRepository.TABLE_NAME)
                    .build();

            dynamoDB.deleteTable(request);
        }

        userRepository.init();
    }

    @Test
    public void testValidUser() {
        userRepository.createUser("foo", "bar".toCharArray(), 1, Set.of("USER"));
        given()
                .when()
                .auth()
                .basic("foo", "bar")
                .get(API)
                .then()
                .statusCode(200)
                .body(is("works!"));
    }

    @Test
    public void testValidUserWithWrongPassword() {
        userRepository.createUser("foo", "bar".toCharArray(), 1, Set.of("USER"));
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
        userRepository.createUser("foo", "bar".toCharArray(), 1, Set.of("notExistingRole"));
        given()
                .when()
                .auth()
                .basic("foo", "bar")
                .get(API)
                .then()
                .statusCode(403);
    }
}