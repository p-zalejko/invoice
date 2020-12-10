package com.gmail.pzalejko.invoice.invoicerequest.web

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/api/v1/invoicerequest")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class InvoiceRequestController {

    @GET
    fun get():String {
        return "works!"
    }

}