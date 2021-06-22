package com.gmail.pzalejko.generator.seller.infrastructure;

import com.gmail.pzalejko.generator.seller.model.SellerInfo;
import com.gmail.pzalejko.generator.seller.model.SellerInfoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@ApplicationScoped
public class DynamodBdSellerInfoRepository implements SellerInfoRepository {

    @Inject
    private DynamoDbClient client;

    @Override
    public SellerInfo findSeller(long id) {
        return new SellerInfo(1,"Foo","abc","0001-000-0000-000","091201");
    }
}
