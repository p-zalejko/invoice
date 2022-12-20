package com.gmail.pzalejko.invoice.core.application

import com.gmail.pzalejko.invoice.core.model.subject.*
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Default
import javax.inject.Inject

@ApplicationScoped
class SellerService {

    @Inject
    @field: Default
    lateinit var repository: SellerRepository

    fun save(accountId: Long, request: CreateSellerCommand): InvoiceSeller {
        val seller = InvoiceSeller(
            accountId,
            request.name,
            SubjectAddress(request.street, request.streetNumber, request.city),
            SubjectPolandTaxId(request.taxId),
            BankAccountNumber(request.bankAccount)
        )

        repository.save(seller)
        return seller
    }
}