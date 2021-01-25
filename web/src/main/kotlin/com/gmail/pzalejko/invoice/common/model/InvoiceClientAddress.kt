package com.gmail.pzalejko.invoice.common.model

import com.gmail.pzalejko.invoice.common.ddd.ValueObject

/**
 * Address of the client who must pay off.
 */
data class InvoiceClientAddress(val street: String, val number: String, val city: String) : ValueObject