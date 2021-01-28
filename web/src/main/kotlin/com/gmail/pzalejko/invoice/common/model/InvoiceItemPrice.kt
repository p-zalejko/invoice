package com.gmail.pzalejko.invoice.common.model

import com.gmail.pzalejko.invoice.common.ddd.ValueObject
import java.math.BigDecimal
import java.util.*

/**
 * A price for the item.
 */
data class InvoiceItemPrice(val value: BigDecimal, val taxPercentage: BigDecimal, val currency: Currency) : ValueObject {

    companion object {
        private val SUPPORTED_CURRENCY = Currency.getInstance("PLN")
    }

    init {
        require(value > BigDecimal.ZERO) { "Price must be grater than zero" }
        require(taxPercentage >= BigDecimal.ZERO) { "Price must be grater or equals than zero" }
        require(currency.currencyCode == SUPPORTED_CURRENCY.currencyCode) { "Only PLN (Poland) currency is supported." }
    }
}