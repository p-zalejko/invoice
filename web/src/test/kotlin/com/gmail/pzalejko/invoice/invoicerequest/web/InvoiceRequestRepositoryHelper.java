package com.gmail.pzalejko.invoice.invoicerequest.web;

import com.gmail.pzalejko.invoice.invoicerequest.infrastructure.InvoiceRequestDatabaseRepository;
import com.gmail.pzalejko.invoice.security.SecurityRepositoryHelper;
import lombok.SneakyThrows;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Set;

@ApplicationScoped
public class InvoiceRequestRepositoryHelper {

    @Inject
    DynamoDbClient dynamoDB;
    @Inject
    InvoiceRequestDatabaseRepository repository;
    @Inject
    SecurityRepositoryHelper securityRepositoryHelper;

    @SneakyThrows
    public void setup() {
        if (dynamoDB.listTables().tableNames().contains(InvoiceRequestDatabaseRepository.TABLE_NAME)) {
            DeleteTableRequest request = DeleteTableRequest.builder()
                    .tableName(InvoiceRequestDatabaseRepository.TABLE_NAME)
                    .build();

            dynamoDB.deleteTable(request);
        }

        repository.init();
        Thread.sleep(3_000);

        securityRepositoryHelper.setup();
    }

    public void createUser(String name, char[] pwd, int accountId, Set<String> roles) {
        securityRepositoryHelper.createUser(name, pwd, accountId, roles);
    }
}
