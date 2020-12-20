package com.gmail.pzalejko.invoice.invoicerequest.infrastructure

import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequest
import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequestRepository
import com.gmail.pzalejko.invoice.model.InvoiceNumber
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Default
import javax.inject.Inject

@ApplicationScoped
class InvoiceRequestDatabaseRepository: InvoiceRequestRepository {

    @Inject
    @field: Default
    lateinit var dynamoDB: DynamoDbClient

    override fun findByNumber(number: InvoiceNumber): InvoiceRequest? {
        TODO("Not yet implemented")
    }

    override fun save(request: InvoiceRequest) {
        TODO("Not yet implemented")
    }

    override fun findLast(month: Int, year: Int): InvoiceRequest? {
        TODO("Not yet implemented")
    }

}