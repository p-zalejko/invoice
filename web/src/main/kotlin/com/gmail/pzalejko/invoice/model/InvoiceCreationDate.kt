package com.gmail.pzalejko.invoice.model

import com.gmail.pzalejko.invoice.common.ValueObject
import java.time.Instant

/**
 * A date when the invoice has been created.
 */
data class InvoiceCreationDate(val date: Instant) : ValueObject {

}