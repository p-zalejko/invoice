package com.gmail.pzalejko.invoice.invoicerequest.infrastructure

import com.gmail.pzalejko.invoice.invoicerequest.model.DefaultInvoiceRequest
import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequest
import com.gmail.pzalejko.invoice.model.InvoiceClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.util.HashMap
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DynamoDbInvoiceRequestFactory {

    fun to(request: DefaultInvoiceRequest): Map<String, AttributeValue> {
        val creationDate = request.getCreationDate()
        val saleDate = request.getSaleDate()
        val paymentDate = request.getPaymentDate()
        val yearMonth = String.format("%d-%d", creationDate.date.year, creationDate.date.monthValue)
        val client = toClient(request.client)

        val invoiceNumberValue = request.getInvoiceNumber().getNumber().toString()
        val invoiceFullNumber = request.getInvoiceNumber().getFullNumber()

        val map: MutableMap<String, AttributeValue> = HashMap()
//        map["InvoiceNumberValue"] = AttributeValue.builder().m(invoiceNumberValue).build()
//        map["InvoiceNumber"] = AttributeValue.builder().n(invoiceFullNumber).build()


        return map
    }

    fun toClient(client: InvoiceClient): Map<String, AttributeValue> {
        val map: MutableMap<String, AttributeValue> = HashMap()
        map["clientAddressCity"] = toStringAttr(client.address.city)
        map["clientAddressNumber"] = toStringAttr(client.address.number)
        map["clientAddressStreet"] = toStringAttr(client.address.street)
        map["clientName"] = toStringAttr(client.name)
        map["clientTaxId"] = toStringAttr(client.taxId.getTaxId())
        return map
    }

    fun toStringAttr(value: String): AttributeValue {
        return AttributeValue.builder().s(value).build()
    }
}