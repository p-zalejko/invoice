package com.gmail.pzalejko.invoice.model

import com.gmail.pzalejko.invoice.common.ValueObject
import java.time.Instant

/**
 * A date (at most) the invoice must be payed off.
 */
data class InvoicePaymentDueDate(val date: Instant) : ValueObject {

    init {
        require(date.isAfter(Instant.now())) { "Date cannot be from the past" }
    }
}