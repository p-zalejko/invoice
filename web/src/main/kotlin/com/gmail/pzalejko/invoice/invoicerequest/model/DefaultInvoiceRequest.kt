package com.gmail.pzalejko.invoice.invoicerequest.model

import com.gmail.pzalejko.invoice.model.*

data class DefaultInvoiceRequest(
    val invNumber: InvoiceNumber,
    val items: MutableList<InvoiceItem>,
    var client: InvoiceClient,
    var dueDate: InvoicePaymentDueDate,
    var sDate: InvoiceSaleDate,
    var cDate: InvoiceCreationDate,
    var state: State
) : InvoiceRequest {

    companion object {

        const val MAX_INVOICE_ITEMS = 20

    }

    override fun getInvoiceNumber(): InvoiceNumber {
        return invNumber
    }

    override fun getCreationDate(): InvoiceCreationDate {
        return cDate
    }

    override fun getPaymentDate(): InvoicePaymentDueDate {
        return dueDate
    }

    override fun getSaleDate(): InvoiceSaleDate {
        return sDate
    }

    override fun changeClient(client: InvoiceClient) {
        this.client = client
    }

    override fun changePaymentDate(dueDate: InvoicePaymentDueDate) {
        this.dueDate = dueDate
    }

    override fun changeSaleDate(saleDate: InvoiceSaleDate) {
        this.sDate = saleDate
    }

    override fun changeCreationDate(creationDate: InvoiceCreationDate) {
        this.cDate = creationDate
    }

    override fun addItem(item: InvoiceItem) {
        if (this.items.size > MAX_INVOICE_ITEMS) {
            throw TooManyInvoiceItemsException("Invoice already contains the maximum number of items - $MAX_INVOICE_ITEMS")
        }

        items.add(item)
    }

    override fun removeItem(item: InvoiceItem) {
        items.remove(item)
    }

    override fun markAsRequested() {
        this.state = State.REQUESTED
    }

    enum class State {
        NEW,
        REQUESTED
    }
}