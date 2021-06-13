package com.gmail.pzalejko.invoiceinput.infrastructure;

import com.gmail.pzalejko.invoiceinput.model.SellerInfoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@ApplicationScoped
public class DynamodBdSellerInfoRepository implements SellerInfoRepository {

    @Inject
    private DynamoDbClient client;
}
