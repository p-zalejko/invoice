package com.gmail.pzalejko.generator.invoiceinput.infrastructure;

import com.gmail.pzalejko.generator.invoiceinput.model.InvoiceInput;
import com.gmail.pzalejko.generator.invoiceinput.model.InvoiceInputRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Objects;

@ApplicationScoped
class SQSInvoiceInputRepository implements InvoiceInputRepository {

    @Override
    public void save(InvoiceInput invoiceInput) {
        Objects.requireNonNull(invoiceInput);
    }
}
