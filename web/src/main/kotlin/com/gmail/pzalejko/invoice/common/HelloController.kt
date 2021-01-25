package com.gmail.pzalejko.invoice.common

import javax.annotation.security.RolesAllowed
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/api/v1/hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class HelloController {

    @GET
    @RolesAllowed("USER")
    fun get(): String {
        return "works!"
    }
}