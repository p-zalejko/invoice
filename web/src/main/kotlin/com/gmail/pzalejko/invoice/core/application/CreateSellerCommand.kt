package com.gmail.pzalejko.invoice.core.application

data class CreateSellerCommand(
        val name: String,
        val street: String,
        val streetNumber: String,
        val city: String,
        val taxId: String,
        val bankAccount: String
    )