package com.gmail.pzalejko.invoicegenerator.model;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

public record InvoiceInput(
        long accountId,
        String invNumber,
        List<Item> items,
        String clientDetails,
        LocalDate dueDate,
        LocalDate saleDate,
        LocalDate creationDate) {

    public record Item(
            String name,
            long count,
            String unit,
            rice price) {
    }

    public record rice(
            double pricePerOne,
            double taxPerOne,
            Currency currency) {
    }
}
