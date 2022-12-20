package com.gmail.pzalejko.invoice.core.application;

import com.gmail.pzalejko.invoice.core.infrastructure.DynamoDBSellerRepository;
import com.gmail.pzalejko.invoice.core.model.subject.SellerRepository;
import com.gmail.pzalejko.invoice.invoicerequest.infrastructure.InvoiceRequestDatabaseRepository;
import com.gmail.pzalejko.invoice.security.SecurityRepositoryHelper;
import lombok.SneakyThrows;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Set;

@ApplicationScoped
public class SellerRepositoryHelper {

    @Inject
    DynamoDbClient dynamoDB;
    @Inject
    DynamoDBSellerRepository repository;
    @Inject
    SecurityRepositoryHelper securityRepositoryHelper;

    @SneakyThrows
    public void setup() {
        clear();

        repository.init();
        Thread.sleep(1_000);

        securityRepositoryHelper.setup();
    }

    public void clear() {
        if (dynamoDB.listTables().tableNames().contains(DynamoDBSellerRepository.TABLE_NAME)) {
            DeleteTableRequest request = DeleteTableRequest.builder()
                    .tableName(DynamoDBSellerRepository.TABLE_NAME)
                    .build();

            dynamoDB.deleteTable(request);
        }

        securityRepositoryHelper.clear();
    }

    public void createUser(String name, char[] pwd, int accountId, Set<String> roles) {
        createUser(name, pwd, accountId, roles, true);
    }

    public void createUser(String name, char[] pwd, int accountId, Set<String> roles, boolean createAccount) {
        securityRepositoryHelper.createUser(name, pwd, accountId, roles, createAccount);
    }
}
