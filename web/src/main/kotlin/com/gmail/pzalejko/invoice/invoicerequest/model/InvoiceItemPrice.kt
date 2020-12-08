package com.gmail.pzalejko.invoice.invoicerequest.model

import com.gmail.pzalejko.invoice.common.ValueObject
import java.math.BigDecimal
import java.util.*

/**
 * A price for the item.
 */
data class InvoiceItemPrice(val value: BigDecimal, val currency: Currency) : ValueObject {

    companion object {
        private val SUPPORTED_LOCALE = Locale("pl", "PL")
        private val SUPPORTED_CURRENCY = Currency.getInstance(SUPPORTED_LOCALE)
    }

    init {
        require(value > BigDecimal.ZERO) { "Price must be grater than zero" }
        require(currency == SUPPORTED_CURRENCY) { "Only PLN (Poland) currency is supported." }
    }
}