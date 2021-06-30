package com.gmail.pzalejko.invoice.invoicerequest.model

import com.gmail.pzalejko.invoice.core.model.invoice.*
import com.gmail.pzalejko.invoice.core.model.subject.InvoiceClient
import com.gmail.pzalejko.invoice.core.model.subject.InvoiceSeller

data class DefaultInvoiceRequest(
    val _seller: InvoiceSeller,
    val invNumber: InvoiceNumber,
    val itemsList: MutableList<InvoiceItem>,
    var clientValue: InvoiceClient,
    var dueDate: InvoicePaymentDueDate,
    var _saleDate: InvoiceSaleDate,
    var _creationDate: InvoiceCreationDate
) : InvoiceRequest {

    companion object {

        const val MAX_INVOICE_ITEMS = 20

    }

    override fun getSeller(): InvoiceSeller {
        return _seller
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
        return _creationDate
    }

    override fun getPaymentDate(): InvoicePaymentDueDate {
        return dueDate
    }

    override fun getSaleDate(): InvoiceSaleDate {
        return _saleDate
    }

    override fun changeClient(client: InvoiceClient) {
        this.clientValue = client
    }

    override fun changePaymentDate(dueDate: InvoicePaymentDueDate) {
        this.dueDate = dueDate
    }

    override fun changeSaleDate(saleDate: InvoiceSaleDate) {
        this._saleDate = saleDate
    }

    override fun changeCreationDate(creationDate: InvoiceCreationDate) {
        this._creationDate = creationDate
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