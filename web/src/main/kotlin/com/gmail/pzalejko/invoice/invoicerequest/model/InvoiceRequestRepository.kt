package com.gmail.pzalejko.invoice.invoicerequest.model

import com.gmail.pzalejko.invoice.invoice.model.InvoiceNumber

interface InvoiceRequestRepository {

    fun findByNumber(accountId: Int, number: InvoiceNumber): InvoiceRequest?

    fun save(request: InvoiceRequest)

    fun findLast(accountId: Long, month: Int, year: Int): InvoiceRequest?
}