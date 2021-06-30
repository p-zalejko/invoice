package com.gmail.pzalejko.invoice.invoice.model

import com.gmail.pzalejko.invoice.common.ddd.ValueObject

/**
 * An item on the invoice.
 */
data class InvoiceItem(val name: String, val count: Int, val unit: InvoiceItemUnit, val pricePerOne: InvoiceItemPrice) : ValueObject {

    init {
        require(count > 0) { "Count must be grater than zero" }
    }
}