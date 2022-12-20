package com.gmail.pzalejko.invoice.invoicerequest.infrastructure

import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequest
import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequestRepository
import com.gmail.pzalejko.invoice.core.model.invoice.InvoiceNumber
import org.jboss.logging.Logger
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*
import java.lang.IllegalStateException
import java.util.*
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Default
import javax.inject.Inject
import software.amazon.awssdk.services.dynamodb.model.ScanRequest

@ApplicationScoped
class InvoiceRequestDatabaseRepository : InvoiceRequestRepository {

    private val LOG: Logger = Logger.getLogger(InvoiceRequestDatabaseRepository::class.java)

    companion object {
        const val TABLE_NAME = "InvoiceApp_InvoiceRequests"
    }

    @Inject
    @field: Default
    lateinit var dynamoDB: DynamoDbClient

    @Inject
    @field: Default
    lateinit var factory: DynamoDbInvoiceRequestFactory

    @PostConstruct
    fun init() {
        val tables = dynamoDB.listTables()
        if (!tables.tableNames().contains(TABLE_NAME)) {
            LOG.info("Table $TABLE_NAME does not exist")

            // key
            val keySchema = ArrayList<KeySchemaElement>()
            keySchema.add(
                KeySchemaElement.builder().attributeName("accountId_invoiceFullNumber").keyType(KeyType.HASH).build()
            )
            keySchema.add(KeySchemaElement.builder().attributeName("yearMonth").keyType(KeyType.RANGE).build())

            // attributes
            val attributes = ArrayList<AttributeDefinition>()
            attributes.add(
                AttributeDefinition.builder().attributeName("accountId_invoiceFullNumber").attributeType("S").build()
            )
            attributes.add(AttributeDefinition.builder().attributeName("yearMonth").attributeType("S").build())

            val throughput = ProvisionedThroughput.builder().readCapacityUnits(1).writeCapacityUnits(1).build()

            val request = CreateTableRequest.builder()
                .tableName(TABLE_NAME)
                .keySchema(keySchema)
                .attributeDefinitions(attributes)
                .provisionedThroughput(throughput)
                .build()

            val createTable = dynamoDB.createTable(request)
            LOG.info("Table $createTable created")
        }
    }

    override fun findByNumber(accountId: Int, number: InvoiceNumber): InvoiceRequest? {
        val queryValue = accountId.toString().plus("_").plus(number.getFullNumber())
        val expressionAttributeValues: MutableMap<String, AttributeValue> = HashMap()
        expressionAttributeValues[":accountId_invoiceFullNumber"] = AttributeValue.builder().s(queryValue).build()
        val query = QueryRequest.builder()
            .keyConditionExpression("accountId_invoiceFullNumber = :accountId_invoiceFullNumber")
            .expressionAttributeValues(expressionAttributeValues)
            .tableName(TABLE_NAME)
            .build()

        val queryResponse = dynamoDB.query(query)
        if (queryResponse.count() == 0) {
            return null
        }

        if (queryResponse.count() != 1) {
            throw IllegalStateException("More than one invoice exist for the given number ${number.getFullNumber()}")
        }

        return factory.from(queryResponse.items().first())
    }

    override fun save(request: InvoiceRequest) {
        val item = factory.to(request)

        val put = PutItemRequest.builder()
            .tableName(TABLE_NAME)
            .item(item)
            .conditionExpression("attribute_not_exists(accountId_invoiceFullNumber)")
            .build()

        val putResult = dynamoDB.putItem(put)
        LOG.debug("Saved a new request $putResult")
    }

    override fun findLast(accountId: Long, month: Int, year: Int): InvoiceRequest? {
        val expressionAttributeValues: MutableMap<String, AttributeValue> = HashMap()
        expressionAttributeValues[":accountId"] = AttributeValue.builder().n(accountId.toString()).build()
        expressionAttributeValues[":yearMonth"] = AttributeValue.builder().s("$year-$month").build()

        val scanRequest: ScanRequest = ScanRequest.builder()
            .tableName(TABLE_NAME)
            .filterExpression("accountId = :accountId and yearMonth = :yearMonth")
            .expressionAttributeValues(expressionAttributeValues)
            .build()


        val queryResponse = dynamoDB.scan(scanRequest)
        if (queryResponse.count() == 0) {
            return null
        }

        val items = queryResponse.items()
        val existingInvoiceRequests = items.map { factory.from(it) }
        val sorted = existingInvoiceRequests.sortedBy { it.getInvoiceNumber().getNumber() }
        return sorted[sorted.lastIndex]
    }

}