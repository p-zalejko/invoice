package com.gmail.pzalejko.invoice.model

/**
 * An invoice number based on the month and year. The number starts from 0 for each month.
 */
data class MonthBasedInvoiceNumber(val num: Long, val month: Long, val year: Long) : InvoiceNumber{

    override fun getNumber(): String {
        return "$num/$month/$year"
    }
}