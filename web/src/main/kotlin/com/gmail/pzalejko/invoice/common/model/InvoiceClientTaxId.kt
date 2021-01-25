package com.gmail.pzalejko.invoice.common.model

import com.gmail.pzalejko.invoice.common.ddd.ValueObject

/**
 * TaxID of the client who must pay off.
 */
interface InvoiceClientTaxId : ValueObject {

  fun getTaxId(): String
}