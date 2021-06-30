package com.gmail.pzalejko.invoice.security;

import com.gmail.pzalejko.invoice.core.infrastructure.DynamoDBSellerRepository;
import com.gmail.pzalejko.invoice.core.model.subject.BankAccountNumber;
import com.gmail.pzalejko.invoice.core.model.subject.InvoiceSeller;
import com.gmail.pzalejko.invoice.core.model.subject.SubjectAddress;
import com.gmail.pzalejko.invoice.core.model.subject.SubjectPolandTaxId;
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
    @Inject
    DynamoDBSellerRepository sellerRepository;

    @SneakyThrows
    public void setup() {
        clear();
        userRepository.init();
        sellerRepository.init();

        Thread.sleep(1_000);
    }

    public void clear() {
        if (dynamoDB.listTables().tableNames().contains(DynamoDbUserRepository.TABLE_NAME)) {
            DeleteTableRequest request = DeleteTableRequest.builder()
                    .tableName(DynamoDbUserRepository.TABLE_NAME)
                    .build();

            dynamoDB.deleteTable(request);
        }

        if (dynamoDB.listTables().tableNames().contains(DynamoDBSellerRepository.TABLE_NAME)) {
            DeleteTableRequest request = DeleteTableRequest.builder()
                    .tableName(DynamoDBSellerRepository.TABLE_NAME)
                    .build();

            dynamoDB.deleteTable(request);
        }
    }

    public void createUser(String name, char[] pwd, int accountId, Set<String> roles) {
        createUser(name,pwd,accountId,roles,true);
    }

    public void createUser(String name, char[] pwd, int accountId, Set<String> roles, boolean createAccount) {
        userRepository.createUser(name, pwd, accountId, roles);

        if (createAccount) {
            BankAccountNumber bankAccount = new BankAccountNumber("09876543210987654321123456");
            SubjectPolandTaxId taxId = new SubjectPolandTaxId("0987654321");
            SubjectAddress address = new SubjectAddress("adr", "0", "ZG");
            InvoiceSeller seller = new InvoiceSeller(accountId, "foo", address, taxId, bankAccount);
            sellerRepository.save(seller);
        }
    }
}
