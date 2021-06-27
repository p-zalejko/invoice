package com.gmail.pzalejko.invoice.invoicerequest.application

import com.gmail.pzalejko.invoice.security.model.InvoiceUserPrincipal
import io.quarkus.runtime.annotations.RegisterForReflection
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.enterprise.inject.Default
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@Path("/api/v1/invoicerequest")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class InvoiceRequestController {

    @Inject
    @field: Default
    lateinit var service: InvoiceService

    @POST
    @RolesAllowed("USER")
    fun create(request: RequestInvoiceCommand, @Context sec: SecurityContext): Response {
        val principal = sec.userPrincipal as InvoiceUserPrincipal
        val createdInvoiceRequest = service.requestInvoice(principal.accountId, request)
        val dto = RequestResponse(createdInvoiceRequest.getFullNumber())

        return Response.ok(dto).status(Response.Status.CREATED).build();
    }

    @RegisterForReflection
    data class RequestResponse(val invoiceNumber: String)
}