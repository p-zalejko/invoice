package com.gmail.pzalejko.invoice.core.model.subject

import com.gmail.pzalejko.invoice.common.ddd.ValueObject

data class BankAccountNumber(val number: String) : ValueObject{

    init {
        require(number.length != 26) { "Bank account has an invalid length" }
    }
}