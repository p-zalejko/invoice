package com.gmail.pzalejko.invoicegenerator.model;

import java.util.Currency;

public record InvoiceItemInput(
        String name,
        long count,
        String unit,
        double pricePerOne,
        double taxPerOne,
        Currency currency) {
}
