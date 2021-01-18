package com.gmail.pzalejko.invoice.invoicerequest.model

import com.gmail.pzalejko.invoice.model.*

data class DefaultInvoiceRequest(
    val invNumber: InvoiceNumber,
    val itemsList: MutableList<InvoiceItem>,
    var clientValue: InvoiceClient,
    var dueDate: InvoicePaymentDueDate,
    var sDate: InvoiceSaleDate,
    var cDate: InvoiceCreationDate
) : InvoiceRequest {

    companion object {

        const val MAX_INVOICE_ITEMS = 20

    }

    override fun getAccountId(): Long {
        return 1 //FIXME: integrate with the security layer
    }
    override fun getItems(): List<InvoiceItem> {
        return itemsList
    }
    override fun getClient(): InvoiceClient {
        return clientValue
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
        this.clientValue = client
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
        if (this.itemsList.size > MAX_INVOICE_ITEMS) {
            throw TooManyInvoiceItemsException("Invoice already contains the maximum number of items - $MAX_INVOICE_ITEMS")
        }

        itemsList.add(item)
    }

    override fun removeItem(item: InvoiceItem) {
        itemsList.remove(item)
    }

}