package com.gmail.pzalejko.invoice.invoicerequest.infrastructure

import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequest
import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequestRepository
import com.gmail.pzalejko.invoice.model.InvoiceNumber
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class InvoiceRequestDatabaseRepository: InvoiceRequestRepository {

    override fun findByNumber(number: InvoiceNumber): InvoiceRequest? {
        TODO("Not yet implemented")
    }

    override fun save(request: InvoiceRequest) {
        TODO("Not yet implemented")
    }

    override fun findLast(month: Int, year: Int): InvoiceRequest? {
        TODO("Not yet implemented")
    }

}