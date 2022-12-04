package com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company;

import com.gmail.pzalejko.invoice.manager.domain.common.Aggregate;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
@ToString
public class Company implements Aggregate<Company> {

    private CompanyId id;
    private final @NonNull Name name;
    private final @NonNull Address address;
    private final @NonNull TaxId companyTaxId;
    private final @NonNull BankAccountNumber bankAccountNumber;
}
