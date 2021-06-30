package com.gmail.pzalejko.invoice.core.model.invoice

import com.gmail.pzalejko.invoice.common.ddd.ValueObject
import java.time.LocalDate

/**
 * A date that points when a good or service  was sold.
 */
data class InvoiceSaleDate(val date: LocalDate) : ValueObject {

    init {
        require(date.isBefore(LocalDate.now()) || date == LocalDate.now()) { "Date cannot be from the future" }
    }
}