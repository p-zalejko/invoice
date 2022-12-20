package com.gmail.pzalejko.invoicegenerator.model;

public interface InvoiceFactory {

    Invoice create(InvoiceInput command);
}
