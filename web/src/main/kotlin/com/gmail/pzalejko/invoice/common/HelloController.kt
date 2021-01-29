package com.gmail.pzalejko.invoice.common

import com.gmail.pzalejko.invoice.security.model.InvoiceUserPrincipal
import javax.annotation.security.RolesAllowed
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

@Path("/api/v1/hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class HelloController {

    @GET
    @RolesAllowed("USER")
    fun get(@Context sec: SecurityContext): String {
        val user = sec.userPrincipal as InvoiceUserPrincipal
        return "Hello ".plus(user.name)
    }
}