package com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company;

import com.gmail.pzalejko.invoice.manager.domain.common.Aggregate;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class Company implements Aggregate<Company> {

    private CompanyId id;
    private final @NonNull Name name;
    private final @NonNull Address address;
    private final @NonNull TaxId companyTaxId;
    private final @NonNull BankAccountNumber bankAccountNumber;
}
