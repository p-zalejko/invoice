package com.gmail.pzalejko.invoice.invoicerequest.web

import com.gmail.pzalejko.invoice.invoicerequest.application.InvoiceService
import com.gmail.pzalejko.invoice.invoicerequest.application.RequestInvoiceCommand
import com.gmail.pzalejko.invoice.model.InvoiceNumber
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.enterprise.inject.Default

@Path("/api/v1/invoicerequest")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class InvoiceRequestController {

    @Inject
    @field: Default
    lateinit var service: InvoiceService

    @GET
    fun get(): String {
        return "works!"
    }

    @POST
    fun create(request: RequestInvoiceCommand): InvoiceNumber {
        return service.requestInvoice(request)
    }
}