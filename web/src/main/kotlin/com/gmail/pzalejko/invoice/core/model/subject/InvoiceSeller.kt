package com.gmail.pzalejko.invoice.core.model.subject

import com.gmail.pzalejko.invoice.common.ddd.ValueObject

/**
 * Details about the company who creates the invoice.
 */
data class InvoiceSeller(
    val accountId: Long,
    val name: String,
    val address: SubjectAddress,
    val taxId: SubjectTaxId,
    val bankAccount: BankAccountNumber
) : ValueObject