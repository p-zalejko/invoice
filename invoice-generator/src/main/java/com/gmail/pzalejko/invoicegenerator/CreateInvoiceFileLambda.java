package com.gmail.pzalejko.invoicegenerator;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

@Named("invoiceGenerator")
public class CreateInvoiceFileLambda implements RequestHandler<JsonNode, InvoiceRequestOutput> {

    @Inject
    Logger log;

    @Override
    public InvoiceRequestOutput handleRequest(JsonNode input, Context context) {
        log.info(String.format("Generating invoice for: %s", input));
        return new InvoiceRequestOutput("ok");
    }
}
