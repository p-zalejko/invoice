package com.gmail.pzalejko.invoice.security;

import com.gmail.pzalejko.invoice.security.infrastructure.DynamoDbUserRepository;
import lombok.SneakyThrows;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Set;

@ApplicationScoped
public class SecurityRepositoryHelper {

    @Inject
    DynamoDbClient dynamoDB;
    @Inject
    DynamoDbUserRepository userRepository;

    @SneakyThrows
    public void setup() {
        if (dynamoDB.listTables().tableNames().contains(DynamoDbUserRepository.TABLE_NAME)) {
            DeleteTableRequest request = DeleteTableRequest.builder()
                    .tableName(DynamoDbUserRepository.TABLE_NAME)
                    .build();

            dynamoDB.deleteTable(request);
        }
        userRepository.init();

        Thread.sleep(3_000);
    }

    public void createUser(String name, char[] pwd, int accountId, Set<String> roles) {
        userRepository.createUser(name, pwd, accountId, roles);
    }
}
