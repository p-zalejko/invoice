package com.gmail.pzalejko.generator.invoiceinput.model;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class InvoiceInputTestData {

    InvoiceInput testData() {
        return testData(5);
    }

    InvoiceInput testData(int items) {
        return new InvoiceInput(
                new SellerInfo(1L, "", "", "", ""),
                new ClientInfo("", "", ""),
                "1",
                testItems(items),
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now()
        );
    }

    List<InvoiceInput.Item> testItems(int items) {
        return IntStream.rangeClosed(1, items)
                .mapToObj(i -> new InvoiceInput.Item(
                        "item_" + i,
                        i,
                        "COUNT",
                        new InvoiceInput.Price(10, 10, Currency.getInstance("PLN"))
                ))
                .collect(Collectors.toList());
    }
}
