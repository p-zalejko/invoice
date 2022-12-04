package com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure;

import com.github.javafaker.Faker;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestDataFactory {

    public static Company newCompany() {
        var faker = new Faker();
        var fakeAddress = faker.address();
        var address = new Address(fakeAddress.streetAddress(), fakeAddress.buildingNumber(), fakeAddress.zipCode(), fakeAddress.city(), fakeAddress.country());

        return new Company(
                null,
                new Name(faker.company().name()),
                address,
                new TaxId("001"),
                new BankAccountNumber("000")
        );
    }
}
