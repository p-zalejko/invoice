package com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure;

import com.gmail.pzalejko.invoice.manager.db.enums.ItemUnit;
import com.gmail.pzalejko.invoice.manager.db.tables.records.InvoiceRecord;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.Configuration;
import org.jooq.DSLContext;

import java.math.BigDecimal;
import java.util.Optional;

import static com.gmail.pzalejko.invoice.manager.db.tables.Company.COMPANY;
import static com.gmail.pzalejko.invoice.manager.db.tables.CompanyAddress.COMPANY_ADDRESS;
import static com.gmail.pzalejko.invoice.manager.db.tables.Invoice.INVOICE;
import static com.gmail.pzalejko.invoice.manager.db.tables.Invoiceitem.INVOICEITEM;

@RequiredArgsConstructor
class JooqInvoiceRepository implements InvoiceRepository {

    @NonNull
    private final DSLContext dsl;

    @Override
    public Invoice save(@NonNull Invoice invoice) {
        var number = invoice.getNumber().value();
        var dueDate = invoice.getDueDate().value();
        var issueDate = invoice.getIssueDate().value();
        var billTo = invoice.getBillToCompany().getId().value();
        var from = invoice.getFromCompany().getId().value();
        var items = invoice.getItems();

        var record = dsl.transactionResult((Configuration trx) -> {
            var inv = dsl
                    .insertInto(INVOICE,
                            INVOICE.NUMBER,
                            INVOICE.INVOICE_DATE,
                            INVOICE.DUE_DATE,
                            INVOICE.COMPANY_BILLTO_ID,
                            INVOICE.COMPANY_FROM_ID
                    )
                    .values(number, issueDate, dueDate, billTo, from)
                    .returning()
                    .fetchOne();

            for (var item : items) {
                dsl
                        .insertInto(INVOICEITEM,
                                INVOICEITEM.INVOICE_ID,
                                INVOICEITEM.ITEM_ID,
                                INVOICEITEM.UNIT,
                                INVOICEITEM.NAME,
                                INVOICEITEM.PRICE_CURRENCY,
                                INVOICEITEM.PRICE_VALUE,
                                INVOICEITEM.PRICE_VAT,
                                INVOICEITEM.QUANTITY
                        )
                        .values(
                                inv.getId(),
                                item.getItem().id().value(),
                                ItemUnit.valueOf(item.getItem().unit().name()),
                                item.getItem().name().value(),
                                item.getItem().price().currency().currencyCode(),
                                BigDecimal.valueOf(item.getItem().price().value()),
                                item.getItem().price().vatPercentage().value(),
                                item.getQuantity()
                        )
                        .execute();
            }

            return inv;
        });

        return invoice.toBuilder()
                .id(new InvoiceId(record.getId()))
                .build();
    }

    @Override
    public Optional<Invoice> findById(@NonNull InvoiceId id) {
        var billTo = COMPANY.as("billTo");
        var billToAddress = COMPANY_ADDRESS.as("billToAddress");
        var fromCompany = COMPANY.as("fromCompany");
        var fromCompanyAddress = COMPANY_ADDRESS.as("fromCompanyAddress");

        return dsl.select()
                .from(INVOICE)
                .join(fromCompany).on(INVOICE.COMPANY_FROM_ID.eq(fromCompany.ID))
                .join(fromCompanyAddress).on(fromCompany.ADDRESS_ID.eq(fromCompanyAddress.ID))
                .join(billTo).on(INVOICE.COMPANY_BILLTO_ID.eq(billTo.ID))
                .join(billToAddress).on(billTo.ADDRESS_ID.eq(billToAddress.ID))
                .where(INVOICE.ID.eq(id.value()))
//                .getSQL(ParamType.INLINED);
                .fetchOptional()
                .map(i -> {
                    var invoiceRecord = i.into(INVOICE);
                    var billToRecord = i.into(billTo);
                    var billToAdrRecord = i.into(billToAddress);

                    var fromCompanyRecord = i.into(fromCompany);
                    var fromCompanyAdrRecord = i.into(fromCompanyAddress);

                    var billToComp = JooqCompanyRepository.mapToCompany(billToRecord, billToAdrRecord);
                    var fromComp = JooqCompanyRepository.mapToCompany(fromCompanyRecord, fromCompanyAdrRecord);
                    return mapToInvoice(invoiceRecord, billToComp, fromComp);
                });
    }

    private com.gmail.pzalejko.invoice.manager.domain.invoice.domain.Invoice mapToInvoice(InvoiceRecord invoiceRecord,
                                                                                          com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company billTo,
                                                                                          com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company from) {
        var id = new InvoiceId(invoiceRecord.getId());
        var number = new InvoiceNumber(invoiceRecord.getNumber());
        var dueDate = new DueDate(invoiceRecord.getDueDate());
        var issueDate = new IssueDate(invoiceRecord.getInvoiceDate());

        return new Invoice(
                id,
                number,
                issueDate,
                dueDate, from,
                billTo,
                null
        );
    }
}
