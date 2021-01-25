package com.gmail.pzalejko.invoice.common.model

/**
 * TaxID of the client from Poland.
 */
data class InvoiceClientPolandTaxId(val id: String) : InvoiceClientTaxId {

    init {
        require(id.length == 10) { "PL NIP is invalid" }
    }

    override fun getTaxId(): String {
        return id
    }
}