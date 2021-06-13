package com.gmail.pzalejko.invoiceinput.application;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.gmail.pzalejko.invoiceinput.model.SellerInfoRepository;
import com.gmail.pzalejko.invoiceinput.model.InvoiceInputFactory;
import com.gmail.pzalejko.invoiceinput.model.InvoiceInputRepository;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

@Named("invoiceInputGenerator")
public class InvoiceInputLambda implements RequestHandler<JsonNode, Output> {

    @Inject
    Logger log;

    @Inject
    InvoiceInputFactory invoiceInputFactory;

    @Inject
    InvoiceInputRepository repository;
    @Inject
    SellerInfoRepository companyInfoRepository;

    @Override
    public Output handleRequest(JsonNode input, Context context) {
        log.info(String.format("Generating invoice for: %s", input));
        var invoiceInput = invoiceInputFactory.create(input);

        repository.save(invoiceInput);
        return new Output("ok");
    }
}
