package com.gmail.pzalejko.invoice.security.model

import java.security.Principal

data class InvoiceUserPrincipal(val _name: String, val accountId: Long) : Principal {

    override fun getName(): String {
        return _name
    }
}