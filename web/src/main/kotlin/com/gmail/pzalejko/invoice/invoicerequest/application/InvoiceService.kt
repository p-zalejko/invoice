package com.gmail.pzalejko.invoice.invoicerequest.application

import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequestFactory
import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequestRepository
import com.gmail.pzalejko.invoice.model.InvoiceNumber
import com.gmail.pzalejko.invoice.model.MonthBasedInvoiceNumber
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

        return MonthBasedInvoiceNumber(1, 1, 200)
    }
}