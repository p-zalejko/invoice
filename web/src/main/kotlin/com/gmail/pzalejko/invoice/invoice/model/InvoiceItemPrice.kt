package com.gmail.pzalejko.invoice.invoice.model

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
        require(value > BigDecimal.ZERO) { "Price must be grater than zero. It is $value" }
        require(taxPercentage >= BigDecimal.ZERO) { "Tax must be grater or equals than zero. It is $taxPercentage" }
        require(currency.currencyCode == SUPPORTED_CURRENCY.currencyCode) { "Only PLN (Poland) currency is supported. This one isn't: $currency"  }
    }
}