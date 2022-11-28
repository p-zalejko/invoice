package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.item.domain.Item;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    private InvoiceId id;
    private @NonNull InvoiceNumber number;
    private @NonNull IssueDate issueDate;
    private @NonNull DueDate dueDate;
    private @NonNull FromCompany fromCompany;
    private @NonNull BillToCompany billToCompany;
    private @NonNull List<Item> items;
}
