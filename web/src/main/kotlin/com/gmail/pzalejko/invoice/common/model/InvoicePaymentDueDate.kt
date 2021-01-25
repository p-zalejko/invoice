package com.gmail.pzalejko.invoice.common.model

import com.gmail.pzalejko.invoice.common.ddd.ValueObject
import java.time.LocalDate

/**
 * A date (at most) the invoice must be payed off.
 */
data class InvoicePaymentDueDate(val date: LocalDate) : ValueObject {

    init {
        require( date == LocalDate.now() ||  date.isAfter(LocalDate.now())) { "Date cannot be from the past" }
    }
}