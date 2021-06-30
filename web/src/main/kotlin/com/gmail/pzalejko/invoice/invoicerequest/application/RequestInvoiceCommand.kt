package com.gmail.pzalejko.invoice.invoicerequest.application

import com.gmail.pzalejko.invoice.core.model.invoice.InvoiceItemUnit
import java.time.LocalDate

/**
 * A command passed to the service (DDD application service) that creates new invoice requests.
 *
 * The command uses its own data model that acts like DTO objects.
 */
data class RequestInvoiceCommand(
    val paymentDate: LocalDate,
    val saleDate: LocalDate,
    val creationDate: LocalDate,
    val client: RequestInvoiceClient,
    val items: Collection<RequestInvoiceItem>
)

data class RequestInvoiceClient(
    val name: String,
    val address: RequestInvoiceClientAddress,
    val taxId: String
)

data class RequestInvoiceClientAddress(
    val street: String,
    val number: String,
    val city: String
)

data class RequestInvoiceItem(
    val name: String,
    val count: Int,
    val unit: InvoiceItemUnit,
    val pricePerOne: RequestInvoiceItemPrice
)

data class RequestInvoiceItemPrice(
    val value: Double,
    val taxPercentage: Double,
    val currency: String
)

