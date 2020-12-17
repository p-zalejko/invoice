package com.gmail.pzalejko.invoice.invoicerequest.model

import com.gmail.pzalejko.invoice.model.*
import java.time.Instant
import java.time.ZoneOffset
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Default
import javax.inject.Inject

@ApplicationScoped
class InvoiceRequestFactory {

    @Inject
    @field: Default
    lateinit var invoiceRequestRepository: InvoiceRequestRepository

    fun create(
        dueDate: InvoicePaymentDueDate,
        creationDate: InvoiceCreationDate,
        saleDate: InvoiceSaleDate,
        invoiceClient: InvoiceClient,
        items: Collection<InvoiceItem>
    ): InvoiceRequest {

        val invoiceNumber = getNextNumber(creationDate.date)
        return DefaultInvoiceRequest(
            invoiceNumber,
            items.toMutableList(),
            invoiceClient,
            dueDate,
            saleDate,
            creationDate,
            DefaultInvoiceRequest.State.NEW
        )
    }

    private fun getNextNumber(date: Instant): InvoiceNumber {
        val localDate = date.atZone(ZoneOffset.UTC).toLocalDate()
        val month = localDate.monthValue
        val year = localDate.year
        val latest = invoiceRequestRepository.findLast(month, year)

        val nextNumber = (latest?.getInvoiceNumber()?.getNumber() ?: 0) + 1
        return MonthBasedInvoiceNumber(nextNumber, month, year)
    }
}