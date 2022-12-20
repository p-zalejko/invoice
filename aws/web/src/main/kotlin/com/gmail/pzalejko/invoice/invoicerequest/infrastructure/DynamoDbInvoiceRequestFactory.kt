package com.gmail.pzalejko.invoice.invoicerequest.infrastructure

import com.gmail.pzalejko.invoice.core.model.invoice.*
import com.gmail.pzalejko.invoice.invoicerequest.model.DefaultInvoiceRequest
import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequest
import com.gmail.pzalejko.invoice.core.model.subject.*
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
            SubjectAddress(
                request["client"]!!.m()["clientAddressStreet"]!!.s(),
                request["client"]!!.m()["clientAddressNumber"]!!.s(),
                request["client"]!!.m()["clientAddressCity"]!!.s()
            ),
            SubjectPolandTaxId(request["client"]!!.m()["clientTaxId"]!!.s())
        )

        val seller = InvoiceSeller(
            request["seller"]!!.m()["sellerId"]!!.n().toLong(),
            request["seller"]!!.m()["sellerName"]!!.s(),
            SubjectAddress(
                request["seller"]!!.m()["sellerAddressStreet"]!!.s(),
                request["seller"]!!.m()["sellerAddressNumber"]!!.s(),
                request["seller"]!!.m()["sellerAddressCity"]!!.s()
            ),
            SubjectPolandTaxId(request["seller"]!!.m()["sellerTaxId"]!!.s()),
            BankAccountNumber(request["seller"]!!.m()["sellerBankAccount"]!!.s())
        )

        val creationDate = InvoiceCreationDate(LocalDate.parse(request["creationDate"]!!.s()))
        val paymentDueDate = InvoicePaymentDueDate(LocalDate.parse(request["paymentDate"]!!.s()))
        val saleDate = InvoiceSaleDate(LocalDate.parse(request["saleDate"]!!.s()))

        return DefaultInvoiceRequest(seller, invoiceNumber, items, client, paymentDueDate, saleDate, creationDate)
    }

    fun to(request: InvoiceRequest): Map<String, AttributeValue> {
        val creationDate = request.getCreationDate().date
        val saleDate = request.getSaleDate().date.toString()
        val paymentDate = request.getPaymentDate().date.toString()
        val yearMonth = String.format("%d-%d", creationDate.year, creationDate.monthValue)

        val client = request.getClient()
        val seller = request.getSeller()
        val items = request.getItems()
        val accountId = seller.accountId

        val invoiceNumberValue = request.getInvoiceNumber().getNumber()
        val invoiceFullNumber = request.getInvoiceNumber().getFullNumber()

        val map: MutableMap<String, AttributeValue> = HashMap()
        map["accountId"] = toNumAttr(accountId)
        map["accountId_invoiceFullNumber"] = toStringAttr(accountId.toString().plus("_").plus(invoiceFullNumber))
        map["invoiceFullNumber"] = toStringAttr(invoiceFullNumber)
        map["invoiceNumberValue"] = toNumAttr(invoiceNumberValue)

        map["creationDate"] = toStringAttr(creationDate.toString())
        map["saleDate"] = toStringAttr(saleDate)
        map["paymentDate"] = toStringAttr(paymentDate)
        map["yearMonth"] = toStringAttr(yearMonth)

        map["client"] = toClient(client)
        map["seller"] = toSeller(seller)
        map["items"] = toItems(items)

        return map
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

    private fun toSeller(seller: InvoiceSeller): AttributeValue {
        val map: MutableMap<String, AttributeValue> = HashMap()
        map["sellerId"] = toNumAttr(seller.accountId)
        map["sellerAddressCity"] = toStringAttr(seller.address.city)
        map["sellerAddressNumber"] = toStringAttr(seller.address.number)
        map["sellerAddressStreet"] = toStringAttr(seller.address.street)
        map["sellerName"] = toStringAttr(seller.name)
        map["sellerTaxId"] = toStringAttr(seller.taxId.getTaxId())
        map["sellerBankAccount"] = toStringAttr(seller.bankAccount.number)

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