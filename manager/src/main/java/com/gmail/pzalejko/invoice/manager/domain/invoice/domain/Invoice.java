package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class Invoice {

    private InvoiceId id;
    private @NonNull InvoiceNumber number;
    private @NonNull IssueDate issueDate;
    private @NonNull DueDate dueDate;
    private @NonNull Company fromCompany;
    private @NonNull Company billToCompany;
    private @NonNull List<InvoiceItem> items;
}
