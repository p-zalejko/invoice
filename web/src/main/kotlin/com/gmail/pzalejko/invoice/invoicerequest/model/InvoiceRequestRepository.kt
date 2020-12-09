package com.gmail.pzalejko.invoice.invoicerequest.model

import com.gmail.pzalejko.invoice.model.InvoiceNumber

interface InvoiceRequestRepository {

    fun findByNumber(number: InvoiceNumber): InvoiceRequest?

    fun save(request: InvoiceRequest)
}