package com.gmail.pzalejko.invoice.invoicerequest.application

import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequestFactory
import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequestRepository
import com.gmail.pzalejko.invoice.model.*
import java.math.BigDecimal
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Default
import javax.inject.Inject

/**
 * A DDD application service that handles commands from the outside world.
 *
 * The InvoiceService creates new invoice requests in the system.
 */
@ApplicationScoped
class InvoiceService {

    @Inject
    @field: Default
    lateinit var invoiceRequestFactory: InvoiceRequestFactory

    @Inject
    @field: Default
    lateinit var invoiceRequestRepository: InvoiceRequestRepository

    fun requestInvoice(request: RequestInvoiceCommand): InvoiceNumber {
        val dueDate = InvoicePaymentDueDate(request.paymentDate)
        val creationDate = InvoiceCreationDate(request.creationDate)
        val saleDate = InvoiceSaleDate(request.saleDate)
        val client = toClient(request)
        val items = toItems(request)

        //FIXME: configure security layer and get the accountId from the user's context
        val invoice = invoiceRequestFactory.create(1L, dueDate, creationDate, saleDate, client, items)
        invoiceRequestRepository.save(invoice)

        return invoice.getInvoiceNumber()
    }

    private fun toClient(request: RequestInvoiceCommand): InvoiceClient {
        val adr = InvoiceClientAddress(
            request.client.address.street,
            request.client.address.number,
            request.client.address.city
        )
        val taxId = InvoiceClientPolandTaxId(request.client.taxId)

        return InvoiceClient(request.client.name, adr, taxId)
    }

    private fun toItems(request: RequestInvoiceCommand): Collection<InvoiceItem> {
        return request.items.map {
            InvoiceItem(
                it.name,
                it.count,
                it.unit,
                InvoiceItemPrice(
                    BigDecimal.valueOf(it.pricePerOne.value),
                    BigDecimal.valueOf(it.pricePerOne.taxPercentage),
                    Currency.getInstance(it.pricePerOne.currency)
                )
            )
        }
    }
}