package com.gmail.pzalejko.invoiceinput.infrastructure;

import com.gmail.pzalejko.invoiceinput.model.InvoiceInput;
import com.gmail.pzalejko.invoiceinput.model.InvoiceInputRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Objects;

@ApplicationScoped
class SQSInvoiceInputRepository implements InvoiceInputRepository {

    @Override
    public void save(InvoiceInput invoiceInput) {
        Objects.requireNonNull(invoiceInput);
    }
}
