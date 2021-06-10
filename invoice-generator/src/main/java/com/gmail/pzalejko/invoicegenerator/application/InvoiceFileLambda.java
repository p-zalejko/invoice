package com.gmail.pzalejko.invoicegenerator.application;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.gmail.pzalejko.invoicegenerator.model.Invoice;
import com.gmail.pzalejko.invoicegenerator.model.InvoiceFactory;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

@Named("invoiceGenerator")
public class InvoiceFileLambda implements RequestHandler<JsonNode, InvoiceRequestOutput> {

    @Inject
    Logger log;

    @Inject
    InvoiceFactory invoiceFactory;

    @Inject
    InvoiceInputFactory invoiceInputFactory;

    @Override
    public InvoiceRequestOutput handleRequest(JsonNode input, Context context) {
        log.info(String.format("Generating invoice for: %s", input));
        var invoiceInput = invoiceInputFactory.create(input);

        Invoice invoice = invoiceFactory.create(invoiceInput);
        return new InvoiceRequestOutput("ok", invoice.fileUri());
    }
}
