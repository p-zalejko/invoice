package com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure;

import com.github.javafaker.Faker;
import com.gmail.pzalejko.invoice.manager.domain.common.Currency;
import com.gmail.pzalejko.invoice.manager.domain.common.Price;
import com.gmail.pzalejko.invoice.manager.domain.common.Unit;
import com.gmail.pzalejko.invoice.manager.domain.common.VatPercentage;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.*;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.*;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Description;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Item;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
                new TaxId(randomString(5)),
                new BankAccountNumber(randomString(10))
        );
    }

    public static Item newItem() {
        var name = new com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Name(randomString(10));
        var description = new Description(randomString(10));
        var price = new Price(10, Currency.PLN, new VatPercentage(23));

        return new Item(null, name, description, Unit.HOUR, price);
    }

    private String randomString(int maxLength) {
        return RandomStringUtils.random(maxLength, true, false);
    }

    private String normalize(String value, int maxLength) {
        if (value.length() > maxLength) {
            return value.substring(0, maxLength - 1);
        }
        return value;
    }

    public static Invoice newInvoice(Company companyA, Company companyB, List<Item> items) {

        var invoiceItems = items.stream()
                .map(i -> new InvoiceItem(null, 1, i))
                .toList();

        return new Invoice(
                null,
                new InvoiceNumber(1, 2022, 1),
                new IssueDate(LocalDate.now()),
                new DueDate(LocalDate.now().plusDays(14)),
                companyA,
                companyB,
                invoiceItems
        );
    }
}
