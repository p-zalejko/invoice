package com.gmail.pzalejko.invoicegenerator.model;

import java.time.LocalDate;
import java.util.List;

public record InvoiceInput(
        long accountId,
        String invNumber,
        List<InvoiceItemInput> items,
        String clientDetails,
        LocalDate dueDate,
        LocalDate saleDate,
        LocalDate creationDate) {
}
