package com.gmail.pzalejko.invoice.model

import com.gmail.pzalejko.invoice.common.ValueObject
import java.time.Instant

/**
 * A date that points when a good or service  was sold.
 */
data class InvoiceSaleDate(val date: Instant) : ValueObject {

    init {
        require(date.isBefore(Instant.now()) || date == Instant.now()) { "Date cannot be from the future" }
    }
}