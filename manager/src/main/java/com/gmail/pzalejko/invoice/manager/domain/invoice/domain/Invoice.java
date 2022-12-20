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
    // TODO: even though it's a root aggregate (in my opinion) it does not have to contain full company objects.
    //  Maybe ID is fine, as long as it allows for meeting invariants are business rules t
    private @NonNull Company fromCompany;
    private @NonNull Company billToCompany;
    private @NonNull List<InvoiceItem> items;
}
