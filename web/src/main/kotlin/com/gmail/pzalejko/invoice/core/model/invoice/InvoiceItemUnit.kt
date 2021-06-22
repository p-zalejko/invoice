package com.gmail.pzalejko.invoice.core.model.invoice

import com.gmail.pzalejko.invoice.common.ddd.ValueObject

/**
 * A unit for the item.
 */
enum class InvoiceItemUnit : ValueObject {
    COUNT,
    TIME
}