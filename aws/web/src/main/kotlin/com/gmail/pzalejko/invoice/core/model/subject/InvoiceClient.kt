package com.gmail.pzalejko.invoice.core.model.subject

import com.gmail.pzalejko.invoice.common.ddd.ValueObject

/**
 * Details about the client who must pay off.
 */
data class InvoiceClient(val name: String, val address: SubjectAddress, val taxId: SubjectTaxId) : ValueObject