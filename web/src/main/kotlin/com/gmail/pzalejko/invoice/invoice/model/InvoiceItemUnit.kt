package com.gmail.pzalejko.invoice.invoice.model

import com.gmail.pzalejko.invoice.common.ddd.ValueObject

/**
 * A unit for the item.
 */
enum class InvoiceItemUnit : ValueObject {
    COUNT,
    TIME
}