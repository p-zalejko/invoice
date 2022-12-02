package com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure;

import com.gmail.pzalejko.invoice.manager.db.tables.Company;
import com.gmail.pzalejko.invoice.manager.db.tables.CompanyAddress;
import com.gmail.pzalejko.invoice.manager.db.tables.records.CompanyAddressRecord;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.CompanyRepository;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;

import java.util.Optional;

@RequiredArgsConstructor
class JooqCompanyRepository implements CompanyRepository {

    private final DSLContext dsl;

    @Override
    public Optional<com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company> findById(@NonNull CompanyId id) {
        Record record = dsl.select()
                .from(Company.COMPANY)
                .join(CompanyAddress.COMPANY_ADDRESS).on(CompanyAddress.COMPANY_ADDRESS.ID.eq(Company.COMPANY.ADDRESS_ID))
                .where(Company.COMPANY.ID.eq(id.value()))
                .fetchOne();

        return Optional.ofNullable(record)
                .map(this::mapToCompany);
    }

    private com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company mapToCompany(Record record) {
        var companyRecord = record.into(Company.COMPANY);
        var addressRecord = record.into(CompanyAddress.COMPANY_ADDRESS);


        var id = new CompanyId(companyRecord.getId());
        var name = new Name(companyRecord.getName());
        var taxId = new TaxId(companyRecord.getTaxid());
        var accountNo = new BankAccountNumber(companyRecord.getAccountNumber());
        var address = mapToAddress(addressRecord);

        return new com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company(
                id,
                name,
                address,
                taxId,
                accountNo
        );
    }

    private com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Address mapToAddress(CompanyAddressRecord addressRecord) {
        return addressRecord.into(Address.class);
    }
}
