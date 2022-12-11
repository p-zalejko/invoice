package com.gmail.pzalejko.invoice.manager.domain.invoice.application;

import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.*;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
class CompanyFactory {

    Company toSellerCompany(@NonNull NewSellerCompanyDto dto) {
        Name name = new Name(dto.name());
        Address address = new Address(dto.street(), dto.number(), dto.zip(), dto.city(), dto.country());
        BankAccountNumber bankAccountNumber = new BankAccountNumber(dto.bankAccountNumber());
        TaxId companyTaxId = new TaxId(dto.companyTaxId());

        return new Company(null, name, address, companyTaxId, bankAccountNumber);
    }

    Company toBillCompany(@NonNull NewBillToCompanyDto dto) {
        Name name = new Name(dto.name());
        Address address = new Address(dto.street(), dto.number(), dto.zip(), dto.city(), dto.country());
        TaxId companyTaxId = new TaxId(dto.companyTaxId());

        return new Company(null, name, address, companyTaxId, BankAccountNumber.MISSING);
    }
}
