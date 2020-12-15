package com.gmail.pzalejko.invoice.invoicerequest.infrastructure

import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequest
import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequestRepository
import com.gmail.pzalejko.invoice.model.InvoiceNumber

class InvoiceRequestDatabaseRepository: InvoiceRequestRepository {

    override fun findByNumber(number: InvoiceNumber): InvoiceRequest? {
        TODO("Not yet implemented")
    }

    override fun save(request: InvoiceRequest) {
        TODO("Not yet implemented")
    }

    override fun findLast(month: Long, year: Long): InvoiceRequest? {
        TODO("Not yet implemented")
    }
}