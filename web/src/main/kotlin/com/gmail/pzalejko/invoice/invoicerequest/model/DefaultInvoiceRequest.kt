package com.gmail.pzalejko.invoice.invoicerequest.model

import com.gmail.pzalejko.invoice.model.*

data class DefaultInvoiceRequest(
    val invNumber: InvoiceNumber,
    val items: MutableList<InvoiceItem>,
    var client: InvoiceClient,
    var dueDate: InvoicePaymentDueDate,
    var saleDate: InvoiceSaleDate,
    var creationDate: InvoiceCreationDate,
    var state: State
) : InvoiceRequest {

    companion object {

        const val MAX_INVOICE_ITEMS = 20

    }

    override fun getInvoiceNumber(): InvoiceNumber {
        return invNumber
    }

    override fun changeClient(client: InvoiceClient) {
        this.client = client
    }

    override fun changePaymentDate(dueDate: InvoicePaymentDueDate) {
        this.dueDate = dueDate
    }

    override fun changeSaleDate(saleDate: InvoiceSaleDate) {
        this.saleDate = saleDate
    }

    override fun changeCreationDate(creationDate: InvoiceCreationDate) {
        this.creationDate = creationDate
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