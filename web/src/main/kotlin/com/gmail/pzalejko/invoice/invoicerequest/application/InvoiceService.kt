package com.gmail.pzalejko.invoice.invoicerequest.application

import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequestId

/**
 * A DDD application service that handles commands from the outside world.
 *
 * The InvoiceService creates new invoice requests in the system.
 */
class InvoiceService {

    fun requestInvoice(request: RequestInvoiceCommand): InvoiceRequestId {
        TODO("Not yet implemented")
    }
}