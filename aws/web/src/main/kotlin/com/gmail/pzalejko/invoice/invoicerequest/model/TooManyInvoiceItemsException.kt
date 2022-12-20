package com.gmail.pzalejko.invoice.invoicerequest.model

import java.lang.RuntimeException

class TooManyInvoiceItemsException : RuntimeException {

    constructor(message: String, ex: Exception?) : super(message, ex) {}
    constructor(message: String) : super(message) {}
    constructor(ex: Exception) : super(ex) {}
}