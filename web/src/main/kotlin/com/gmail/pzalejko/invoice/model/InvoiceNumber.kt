package com.gmail.pzalejko.invoice.model

import com.gmail.pzalejko.invoice.common.ValueObject

/**
 * An unique number of the invoice.
 */
interface InvoiceNumber : ValueObject {

    /**
     * a number of the invoice
     */
    fun getNumber(): Int

    /**
     * a postfix part of the invoice number. Depending how invoices are counted (e.g. monthly, yearly or in any other way)
     * it can provide different content.
     */
    fun getNumberPostfix(): String

    /**
     * a full invoice number.
     */
    fun getFullNumber(): String
}