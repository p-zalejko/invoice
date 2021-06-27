package com.gmail.pzalejko.invoice.core.model.subject

interface SellerRepository {

    fun findByAccountId(accountId: Int): InvoiceSeller?

    fun save(seller: InvoiceSeller)
}