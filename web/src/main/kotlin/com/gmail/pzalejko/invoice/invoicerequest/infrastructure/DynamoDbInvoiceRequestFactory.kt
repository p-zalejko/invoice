package com.gmail.pzalejko.invoice.invoicerequest.infrastructure

import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequest
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.util.HashMap
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DynamoDbInvoiceRequestFactory {

    fun to(request: InvoiceRequest): Map<String, AttributeValue>{
        val map: MutableMap<String, AttributeValue> = HashMap()
//        map["InvoiceNumberValue"] =  AttributeValue.builder().n(request.getInvoiceNumber().getNumber()).build()

        return map
    }
}