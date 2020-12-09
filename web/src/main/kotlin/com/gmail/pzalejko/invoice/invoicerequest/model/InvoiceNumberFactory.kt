package com.gmail.pzalejko.invoice.invoicerequest.model

import com.gmail.pzalejko.invoice.model.InvoiceNumber

class InvoiceNumberFactory {

    fun getNextNumber(): InvoiceNumber {
        return InvoiceNumber("")
    }
}
