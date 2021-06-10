package com.gmail.pzalejko.invoicegenerator.model;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

/**
 * Encapsulates all information that must be included in the invoice. It also takes cares of all the calculations
 * like a total price, total tax values etc.
 */
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
            Price price) {
    }

    public record Price(
            double pricePerOne,
            double taxPerOne,
            Currency currency) {
    }
}
