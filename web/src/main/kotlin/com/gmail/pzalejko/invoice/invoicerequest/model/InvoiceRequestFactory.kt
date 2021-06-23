package com.gmail.pzalejko.invoice.invoicerequest.model

import com.gmail.pzalejko.invoice.core.model.invoice.*
import com.gmail.pzalejko.invoice.core.model.subject.*
import java.time.LocalDate
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
        invoiceSeller: InvoiceSeller,
        items: Collection<InvoiceItem>
    ): InvoiceRequest {

        val invoiceNumber = getNextNumber(invoiceSeller.accountId, creationDate.date)
        return DefaultInvoiceRequest(
            invoiceSeller,
            invoiceNumber,
            items.toMutableList(),
            invoiceClient,
            dueDate,
            saleDate,
            creationDate
        )
    }

    private fun getNextNumber(accountId: Long, date: LocalDate): InvoiceNumber {
        val month = date.monthValue
        val year = date.year
        val latest = invoiceRequestRepository.findLast(accountId, month, year)

        val nextNumber = (latest?.getInvoiceNumber()?.getNumber() ?: 0) + 1
        return MonthBasedInvoiceNumber(nextNumber, month, year)
    }
}