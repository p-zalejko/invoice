package com.gmail.pzalejko.invoice.common.infrastructure.web;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyApplicationExceptionHandler implements ExceptionMapper<IllegalArgumentException> 
{
    @Override
    public Response toResponse(IllegalArgumentException exception) 
    {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();  
    }
}