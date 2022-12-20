package com.gmail.pzalejko.invoice.core.application

import com.gmail.pzalejko.invoice.core.model.subject.CannotCreateAccountException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class SellerExceptionMapper : ExceptionMapper<CannotCreateAccountException> {

    override fun toResponse(exception: CannotCreateAccountException?): Response {
        val httpStatus = Response.Status.BAD_REQUEST
        return Response.status(httpStatus).entity("").build()
    }
}