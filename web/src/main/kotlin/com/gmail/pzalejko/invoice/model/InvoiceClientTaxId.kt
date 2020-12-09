package com.gmail.pzalejko.invoice.model

import com.gmail.pzalejko.invoice.common.ValueObject

/**
 * TaxID of the client who must pay off.
 */
interface InvoiceClientTaxId : ValueObject {

  fun getTaxId(): String
}