package com.gmail.pzalejko.invoice.model

import com.gmail.pzalejko.invoice.common.ValueObject

/**
 * Details about the client who must pay off.
 */
data class InvoiceClient(val name: String, val address: InvoiceClientAddress, val taxId: InvoiceClientTaxId) : ValueObject