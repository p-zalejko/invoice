package com.gmail.pzalejko.invoice.invoicerequest.model

import com.gmail.pzalejko.invoice.common.ddd.Aggregate
import com.gmail.pzalejko.invoice.core.model.subject.InvoiceClient
import com.gmail.pzalejko.invoice.core.model.invoice.*

/**
 * An invoice request with all associated information important for invoice generation. The InvoiceRequest controls
 * integrity of the invoice's content:
 * - the list of items must be greater than zero but lower than the allowed MAX. Items must be valid (with all details).
 * - client details must be present
 * - payment date must be provided and cannot be from the past
 * - when the invoice is being generated then its content cannot be changed anymore.
 */
interface InvoiceRequest : Aggregate<InvoiceNumber> {

    fun getSellerId(): Long

    fun getClient(): InvoiceClient

    fun getItems(): List<InvoiceItem>

    fun getInvoiceNumber(): InvoiceNumber

    fun getPaymentDate(): InvoicePaymentDueDate

    fun getSaleDate(): InvoiceSaleDate

    fun getCreationDate(): InvoiceCreationDate

    fun changeClient(client: InvoiceClient)

    fun changePaymentDate(dueDate: InvoicePaymentDueDate)

    fun changeSaleDate(saleDate: InvoiceSaleDate)

    fun changeCreationDate(creationDate: InvoiceCreationDate)

    fun addItem(item: InvoiceItem)

    fun removeItem(item: InvoiceItem)

}