package com.gmail.pzalejko.invoice.invoicerequest.web

import com.gmail.pzalejko.invoice.invoicerequest.application.InvoiceService
import com.gmail.pzalejko.invoice.invoicerequest.application.RequestInvoiceCommand
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.enterprise.inject.Default
import javax.ws.rs.core.Response

@Path("/api/v1/invoicerequest")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class InvoiceRequestController {

    @Inject
    @field: Default
    lateinit var service: InvoiceService

    @GET
    @RolesAllowed("USER")
    fun get(): String {
        return "works!"
    }

    @POST
    @RolesAllowed("USER")
    fun create(request: RequestInvoiceCommand): Response {
        val createdInvoiceRequest = service.requestInvoice(request)
        val dto = RequestResponse(createdInvoiceRequest.getFullNumber())

        return Response.ok(dto).status(Response.Status.CREATED).build();
    }

    data class RequestResponse(val invoiceNumber: String)
}