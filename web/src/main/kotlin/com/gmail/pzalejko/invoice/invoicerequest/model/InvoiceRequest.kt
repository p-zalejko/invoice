package com.gmail.pzalejko.invoice.invoicerequest.model

import com.gmail.pzalejko.invoice.common.Aggregate
import java.util.*

/**
 * An invoice request with all associated information important for invoice generation. The InvoiceRequest controls
 * integrity of the invoice's content:
 * - the list of items must be greater than zero but lower than the allowed MAX. Items must be valid (with all details).
 * - client details must be present
 * - payment date must be provided and cannot be from the past
 */
interface InvoiceRequest : Aggregate {

    fun setClient(client: InvoiceClient)

    fun setPaymentDate(dueDate: InvoicePaymentDueDate)

    fun addItem(item: InvoiceItem)

    fun isValid(): Boolean

    fun request(): UUID
}