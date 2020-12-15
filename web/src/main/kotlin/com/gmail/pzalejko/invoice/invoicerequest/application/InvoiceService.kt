package com.gmail.pzalejko.invoice.invoicerequest.application

import com.gmail.pzalejko.invoice.model.InvoiceNumber
import javax.enterprise.context.ApplicationScoped

/**
 * A DDD application service that handles commands from the outside world.
 *
 * The InvoiceService creates new invoice requests in the system.
 */
@ApplicationScoped
class InvoiceService {

    fun requestInvoice(request: RequestInvoiceCommand): InvoiceNumber {
        TODO("Not yet implemented")
    }
}