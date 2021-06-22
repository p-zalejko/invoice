package com.gmail.pzalejko.invoice.core.model.subject

import com.gmail.pzalejko.invoice.common.ddd.ValueObject

/**
 * TaxID of the client who must pay off.
 */
interface SubjectTaxId : ValueObject {

  fun getTaxId(): String
}