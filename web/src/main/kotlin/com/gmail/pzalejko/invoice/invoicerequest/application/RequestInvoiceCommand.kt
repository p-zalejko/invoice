package com.gmail.pzalejko.invoice.invoicerequest.application

import com.gmail.pzalejko.invoice.model.InvoiceClient
import com.gmail.pzalejko.invoice.model.InvoiceItem
import java.time.Instant

data class RequestInvoiceCommand(
    val paymentDate: Instant,
    val client: InvoiceClient,
    val items: Collection<InvoiceItem>
)