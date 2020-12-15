package com.gmail.pzalejko.invoice.invoicerequest.model

import com.gmail.pzalejko.invoice.model.InvoiceNumber
import com.gmail.pzalejko.invoice.model.MonthBasedInvoiceNumber

class InvoiceNumberFactory {

    fun getNextNumber(): InvoiceNumber {
        return MonthBasedInvoiceNumber(1,1,2020)
    }
}
