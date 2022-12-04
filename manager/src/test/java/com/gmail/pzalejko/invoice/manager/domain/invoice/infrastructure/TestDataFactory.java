package com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure;

import com.github.javafaker.Faker;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestDataFactory {

    public static Company newCompany() {
        var faker = new Faker();
        var fakeAddress = faker.address();
        var address = new Address(
                normalize(fakeAddress.streetAddress(), 25),
                normalize(fakeAddress.buildingNumber(), 10),
                normalize(fakeAddress.zipCode(), 10),
                normalize(fakeAddress.city(), 25),
                normalize(fakeAddress.country(), 25)
        );

        return new Company(
                null,
                new Name(normalize(faker.company().name(), 25)),
                address,
                new TaxId("001"),
                new BankAccountNumber("000")
        );
    }

    private String normalize(String value, int maxLength) {
        if (value.length() > maxLength) {
            return value.substring(0, maxLength - 1);
        }
        return value;
    }
}
