package com.gmail.pzalejko.invoice.core.model.subject

/**
 * TaxID of the client from Poland.
 */
data class SubjectPolandTaxId(val id: String) : SubjectTaxId {

    init {
        require(id.length == 10) { "PL NIP is invalid" }
    }

    override fun getTaxId(): String {
        return id
    }
}