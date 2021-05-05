package com.gmail.pzalejko.invoicegenerator;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

@Named("invoiceGenerator")
public class CreateInvoiceFileLambda implements RequestHandler<InvoiceRequestInput, InvoiceRequestOutput> {

    @Inject
    Logger log;

    @Override
    public InvoiceRequestOutput handleRequest(InvoiceRequestInput input, Context context) {
        log.info(String.format("Generating invoice for %d events", input.getRecords().length));
        InvoiceRequestOutput outputObject = new InvoiceRequestOutput();
        outputObject.setIds(new String[]{});
        return outputObject;
    }
}
