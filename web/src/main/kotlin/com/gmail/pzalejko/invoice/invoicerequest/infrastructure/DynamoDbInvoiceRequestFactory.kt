package com.gmail.pzalejko.invoice.invoicerequest.infrastructure

import com.gmail.pzalejko.invoice.invoicerequest.model.DefaultInvoiceRequest
import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequest
import com.gmail.pzalejko.invoice.model.*
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.time.LocalDate
import java.util.*
import javax.enterprise.context.ApplicationScoped

/**
 * Converts InvoiceRequest objects to dynamoDB structure and vice versa.
 */
@ApplicationScoped
class DynamoDbInvoiceRequestFactory {

    fun from(request: Map<String, AttributeValue>): InvoiceRequest {
        val invoiceNumber = MonthBasedInvoiceNumber(
            request["invoiceNumberValue"]!!.n().toInt(),
            request["yearMonth"]!!.s().split("-")[1].toInt(),
            request["yearMonth"]!!.s().split("-")[0].toInt()
        )
        val items = request["items"]!!.l()
            .map {
                InvoiceItem(
                    it.m()["name"]!!.s(),
                    it.m()["count"]!!.n().toInt(),
                    InvoiceItemUnit.valueOf(it.m()["unit"]!!.s()),
                    InvoiceItemPrice(
                        it.m()["priceValue"]!!.n().toBigDecimal(),
                        it.m()["priceTax"]!!.n().toBigDecimal(),
                        Currency.getInstance(it.m()["priceCurrency"]!!.s())
                    )
                )
            }.toMutableList()

        val client = InvoiceClient(
            request["client"]!!.m()["clientName"]!!.s(),
            InvoiceClientAddress(
                request["client"]!!.m()["clientAddressStreet"]!!.s(),
                request["client"]!!.m()["clientAddressNumber"]!!.s(),
                request["client"]!!.m()["clientAddressCity"]!!.s()
            ),
            InvoiceClientPolandTaxId(request["client"]!!.m()["clientTaxId"]!!.s())
        )

        val accountId = request["accountId"]!!.n().toLong()
        val creationDate = InvoiceCreationDate(LocalDate.parse(request["creationDate"]!!.s()))
        val paymentDueDate = InvoicePaymentDueDate(LocalDate.parse(request["paymentDate"]!!.s()))
        val saleDate = InvoiceSaleDate(LocalDate.parse(request["saleDate"]!!.s()))

        return DefaultInvoiceRequest(accountId, invoiceNumber, items, client, paymentDueDate, saleDate, creationDate)
    }

    fun to(request: InvoiceRequest): Map<String, AttributeValue> {
        val creationDate = request.getCreationDate().date
        val saleDate = request.getSaleDate().date.toString()
        val paymentDate = request.getPaymentDate().date.toString()
        val yearMonth = String.format("%d-%d", creationDate.year, creationDate.monthValue)

        val client = request.getClient()
        val items = request.getItems()
        val accountId = request.getAccountId()

        val invoiceNumberValue = request.getInvoiceNumber().getNumber()
        val invoiceFullNumber = request.getInvoiceNumber().getFullNumber()

        val map: MutableMap<String, AttributeValue> = HashMap()

        map["accountId"] = toNumAttr(accountId)

        map["invoiceFullNumber"] = toStringAttr(invoiceFullNumber)
        map["invoiceNumberValue"] = toNumAttr(invoiceNumberValue)

        // the next filed is a trick - it is a field that contains an invoice number as the key! While saving the
        //  invoice request, this field will be used as the unique constrain - if the field with that key exists then
        //  it means the invoice with that number already exists! This validation is done via a condition expression
        //  called 'attribute_not_exists'.
        map[getUniqueIdentifier(request)] = toNumAttr(0)

        map["creationDate"] = toStringAttr(creationDate.toString())
        map["saleDate"] = toStringAttr(saleDate)
        map["paymentDate"] = toStringAttr(paymentDate)
        map["yearMonth"] = toStringAttr(yearMonth)

        map["client"] = toClient(client)
        map["items"] = toItems(items)

        return map
    }

    fun getUniqueIdentifier(request: InvoiceRequest): String {
        val num = request.getInvoiceNumber().getFullNumber().replace("/", "_")
        return "inv_$num"
    }

    private fun toClient(client: InvoiceClient): AttributeValue {
        val map: MutableMap<String, AttributeValue> = HashMap()
        map["clientAddressCity"] = toStringAttr(client.address.city)
        map["clientAddressNumber"] = toStringAttr(client.address.number)
        map["clientAddressStreet"] = toStringAttr(client.address.street)
        map["clientName"] = toStringAttr(client.name)
        map["clientTaxId"] = toStringAttr(client.taxId.getTaxId())

        return toMapAttr(map)
    }

    private fun toItems(items: List<InvoiceItem>): AttributeValue {
        val result: MutableList<AttributeValue> = mutableListOf()
        for (item in items) {
            val map: MutableMap<String, AttributeValue> = HashMap()

            map["name"] = toStringAttr(item.name)
            map["unit"] = toStringAttr(item.unit.name)
            map["count"] = toNumAttr(item.count)
            map["priceCurrency"] = toStringAttr(item.pricePerOne.currency.currencyCode)
            map["priceTax"] = toNumAttr(item.pricePerOne.taxPercentage.toDouble())
            map["priceValue"] = toNumAttr(item.pricePerOne.value.toDouble())

            result.add(toMapAttr(map))
        }
        return toListAttr(result)
    }

    private fun toStringAttr(value: String): AttributeValue {
        return AttributeValue.builder().s(value).build()
    }

    private fun toNumAttr(value: Number): AttributeValue {
        return AttributeValue.builder().n(value.toString()).build()
    }

    private fun toListAttr(values: List<AttributeValue>): AttributeValue {
        return AttributeValue.builder().l(values).build()
    }

    private fun toMapAttr(values: Map<String, AttributeValue>): AttributeValue {
        return AttributeValue.builder().m(values).build()
    }
}