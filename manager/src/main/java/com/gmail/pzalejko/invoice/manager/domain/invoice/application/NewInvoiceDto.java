package com.gmail.pzalejko.invoice.manager.domain.invoice.application;

import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

public record NewInvoiceDto(int fromCompany,
                            @NonNull LocalDate issueDate,
                            @NonNull LocalDate dueDate,
                            int billToCompany,
                            @NonNull List<NewInvoiceItemsDto> items) {

    record NewInvoiceItemsDto(int itemId, int quantity) {

    }
}
