package com.gmail.pzalejko.invoice.invoicerequest.application

import com.gmail.pzalejko.invoice.model.InvoiceNumber

/**
 * A DDD application service that handles commands from the outside world.
 *
 * The InvoiceService creates new invoice requests in the system.
 */
class InvoiceService {

    fun requestInvoice(request: RequestInvoiceCommand): InvoiceNumber {
        TODO("Not yet implemented")
    }
}