package com.gmail.pzalejko.invoice.model

import com.gmail.pzalejko.invoice.common.ValueObject

/**
 * A unit for the item.
 */
enum class InvoiceItemUnit : ValueObject {
    COUNT,
    TIME
}