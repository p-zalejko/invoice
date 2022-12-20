package com.gmail.pzalejko.invoice.core.model.subject

interface SellerRepository {

    fun findByAccountId(accountId: Long): InvoiceSeller?

    fun save(seller: InvoiceSeller)
}