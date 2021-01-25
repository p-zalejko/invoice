package com.gmail.pzalejko.invoice.invoicerequest.model

import com.gmail.pzalejko.invoice.common.model.InvoiceNumber

interface InvoiceRequestRepository {

    fun findByNumber(accountId: Int, number: InvoiceNumber): InvoiceRequest?

    fun save(request: InvoiceRequest)

    fun findLast(accountId: Int, month: Int, year: Int): InvoiceRequest?
}