package com.gmail.pzalejko.invoicegenerator.infrastructure;

import com.gmail.pzalejko.invoicegenerator.model.Invoice;
import com.gmail.pzalejko.invoicegenerator.model.InvoiceFactory;
import com.gmail.pzalejko.invoicegenerator.model.InvoiceInput;

import javax.enterprise.context.ApplicationScoped;
import java.util.Objects;

@ApplicationScoped
public class PdfInvoiceFactory implements InvoiceFactory {

    @Override
    public Invoice create(InvoiceInput command) {
        Objects.requireNonNull(command);
        return null;
    }
}
