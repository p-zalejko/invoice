package com.gmail.pzalejko.invoice.invoicerequest.model

import com.gmail.pzalejko.invoice.common.Aggregate
import com.gmail.pzalejko.invoice.model.InvoiceClient
import com.gmail.pzalejko.invoice.model.InvoiceItem
import com.gmail.pzalejko.invoice.model.InvoiceNumber
import com.gmail.pzalejko.invoice.model.InvoicePaymentDueDate

/**
 * An invoice request with all associated information important for invoice generation. The InvoiceRequest controls
 * integrity of the invoice's content:
 * - the list of items must be greater than zero but lower than the allowed MAX. Items must be valid (with all details).
 * - client details must be present
 * - payment date must be provided and cannot be from the past
 * - when the invoice is being generated then its content cannot be changed anymore.
 */
interface InvoiceRequest : Aggregate<InvoiceNumber> {

    fun changeClient(client: InvoiceClient)

    fun changePaymentDate(dueDate: InvoicePaymentDueDate)

    fun changeSaleDate(dueDate: InvoicePaymentDueDate)

    fun addItem(item: InvoiceItem)

    fun removeItem(item: InvoiceItem)

    fun markAsRequested()

}