package com.gmail.pzalejko.invoice.core.model.invoice

/**
 * An invoice number based on the month and year. The number starts from 0 for each month.
 */
data class MonthBasedInvoiceNumber(val num: Int, val month: Int, val year: Int) : InvoiceNumber {

    override fun getNumber(): Int {
        return num
    }

    override fun getNumberPostfix(): String {
        return "$month/$year"
    }

    override fun getFullNumber(): String {
        return "$num/$month/$year"
    }
}