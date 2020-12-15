package com.gmail.pzalejko.invoice.model

import com.gmail.pzalejko.invoice.common.ValueObject

/**
 * An unique number of the invoice.
 */
interface InvoiceNumber : ValueObject {

    fun getNumber(): String
}