package com.gmail.pzalejko.invoice.invoicerequest.web

import com.gmail.pzalejko.invoice.invoicerequest.application.InvoiceService
import com.gmail.pzalejko.invoice.invoicerequest.application.RequestInvoiceCommand
import io.quarkus.runtime.annotations.RegisterForReflection
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

    @POST
    @RolesAllowed("USER")
    fun create(request: RequestInvoiceCommand): Response {
        val createdInvoiceRequest = service.requestInvoice(request)
        val dto = RequestResponse(createdInvoiceRequest.getFullNumber())

        return Response.ok(dto).status(Response.Status.CREATED).build();
    }

    @RegisterForReflection
    data class RequestResponse(val invoiceNumber: String)
}