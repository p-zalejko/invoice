package com.gmail.pzalejko.invoice.pdfgenerator.application;

import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

public record InvoiceInput(@NonNull FromCompany fromCompany,
                           @NonNull LocalDate issueDate,
                           @NonNull LocalDate dueDate,
                           @NonNull String paymentMethod,
                           @NonNull String whoCreated,
                           @NonNull BillToCompany billToCompany,
                           @NonNull Summary summary,
                           @NonNull SummaryPerVat summaryPerVat,
                           @NonNull List<Item> items) {

    record Item(String name, int quantity, String itemCode, String unit, int vatPerItem, double netPerItem, double totalNet, double total) {

    }

    record Summary(double totalNet, double totalVat, double total) {

    }

    record SummaryPerVat(int vatPercentage, double vatTotal, double totalNet, double total) {

    }

    record FromCompany(@NonNull String name, @NonNull String bankAccountNumber) {

    }

    record BillToCompany(@NonNull String name) {

    }
}

