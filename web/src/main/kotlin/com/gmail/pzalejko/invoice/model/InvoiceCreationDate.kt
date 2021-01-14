package com.gmail.pzalejko.invoice.model

import com.gmail.pzalejko.invoice.common.ValueObject
import java.time.LocalDate

/**
 * A date when the invoice has been created.
 */
data class InvoiceCreationDate(val date: LocalDate) : ValueObject, Comparable<InvoiceCreationDate> {

    override fun compareTo(other: InvoiceCreationDate): Int {
        return this.date.compareTo(other.date)
    }
}