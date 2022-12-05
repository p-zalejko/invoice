package com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure;

import com.gmail.pzalejko.invoice.manager.db.tables.records.CompanyAddressRecord;
import com.gmail.pzalejko.invoice.manager.db.tables.records.CompanyRecord;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.CompanyRepository;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;

import java.util.Optional;

import static com.gmail.pzalejko.invoice.manager.db.tables.CompanyAddress.COMPANY_ADDRESS;
import static com.gmail.pzalejko.invoice.manager.db.tables.Company.COMPANY;

@RequiredArgsConstructor
class JooqCompanyRepository implements CompanyRepository {

    @NonNull
    private final DSLContext dsl;

    @Override
    public com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company save(@NonNull com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company company) {
        Address address = company.getAddress();
        CompanyRecord companyRecord = dsl.transactionResult((Configuration trx) -> {
            CompanyAddressRecord addressRecord = trx.dsl()
                    .insertInto(COMPANY_ADDRESS,
                            COMPANY_ADDRESS.STREET,
                            COMPANY_ADDRESS.NUMBER,
                            COMPANY_ADDRESS.ZIP,
                            COMPANY_ADDRESS.CITY,
                            COMPANY_ADDRESS.COUNTRY
                    )
                    .values(address.street(), address.number(), address.zip(), address.city(), address.country())
                    .returning()
                    .fetchOne();

            return trx.dsl()
                    .insertInto(COMPANY,
                            COMPANY.ADDRESS_ID,
                            COMPANY.NAME,
                            COMPANY.ACCOUNT_NUMBER,
                            COMPANY.TAXID
                    )
                    .values(addressRecord.getId(), company.getName().value(), company.getBankAccountNumber().value(), company.getCompanyTaxId().value())
                    .returning()
                    .fetchOne();
        });

        return company
                .toBuilder()
                .id(new CompanyId(companyRecord.getId()))
                .build();
    }

    @Override
    public Optional<com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company> findById(@NonNull CompanyId id) {
        Record record = dsl.select()
                .from(COMPANY)
                .join(COMPANY_ADDRESS).on(COMPANY_ADDRESS.ID.eq(COMPANY.ADDRESS_ID))
                .where(COMPANY.ID.eq(id.value()))
                .fetchOne();

        return Optional.ofNullable(record)
                .map(i -> mapToCompany(i.into(COMPANY), i.into(COMPANY_ADDRESS)));
    }

    static com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company mapToCompany(CompanyRecord companyRecord, CompanyAddressRecord addressRecord) {
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

    static com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Address mapToAddress(CompanyAddressRecord addressRecord) {
        return addressRecord.into(Address.class);
    }
}
