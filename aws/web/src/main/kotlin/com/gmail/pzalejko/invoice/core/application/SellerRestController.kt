package com.gmail.pzalejko.invoice.core.application

import com.gmail.pzalejko.invoice.security.model.InvoiceUserPrincipal
import javax.annotation.security.RolesAllowed
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@Path("/api/v1/sellers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class SellerRestController {

    @Inject
    @field: Default
    lateinit var service: SellerService

    @POST
    @RolesAllowed("USER")
    fun create(request: CreateSellerCommand, @Context sec: SecurityContext): Response {
        val principal = sec.userPrincipal as InvoiceUserPrincipal
        val seller = service.save(principal.accountId, request)

        return Response.ok(seller.accountId.toString()).status(Response.Status.CREATED).build();
    }
}