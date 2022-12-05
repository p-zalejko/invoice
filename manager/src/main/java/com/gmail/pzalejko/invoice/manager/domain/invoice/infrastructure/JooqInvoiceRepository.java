package com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure;

import com.gmail.pzalejko.invoice.manager.db.enums.ItemUnit;
import com.gmail.pzalejko.invoice.manager.db.tables.records.CompanyAddressRecord;
import com.gmail.pzalejko.invoice.manager.db.tables.records.CompanyRecord;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.Invoice;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.InvoiceId;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.InvoiceItem;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.InvoiceRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.gmail.pzalejko.invoice.manager.db.tables.Company.COMPANY;
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
        return dsl.select()
                .from(INVOICE)
                .leftJoin(COMPANY).on(INVOICE.COMPANY_FROM_ID.eq(COMPANY.ID))
                .leftJoin(COMPANY).on(INVOICE.COMPANY_BILLTO_ID.eq(COMPANY.ID))
                .where(INVOICE.ID.eq(id.value()))
//                .getSQL(ParamType.INLINED);
                .fetchOptional()
                .map(this::mapToInvoice);
    }

    private com.gmail.pzalejko.invoice.manager.domain.invoice.domain.Invoice mapToInvoice(Record record) {
        var invoiceRecord = record.into(INVOICE);
        var companyRecord2 = record.into(COMPANY);
//
//        var number = new InvoiceNumber(invoiceRecord.getNumber());
//        var dueDate = new DueDate(invoiceRecord.getDueDate());
//        var issueDate =new IssueDate(invoiceRecord.getInvoiceDate());
//        var billTo = invoice.getBillToCompany().getId().value();
//        var from = invoice.getFromCompany().getId().value();

        throw new UnsupportedOperationException("yet");
    }
}
