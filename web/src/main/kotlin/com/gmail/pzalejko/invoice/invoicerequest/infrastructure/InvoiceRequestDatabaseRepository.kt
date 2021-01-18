package com.gmail.pzalejko.invoice.invoicerequest.infrastructure

import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequest
import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequestRepository
import com.gmail.pzalejko.invoice.model.InvoiceNumber
import org.jboss.logging.Logger
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*
import java.lang.IllegalStateException
import java.util.*
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Default
import javax.inject.Inject

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
            keySchema.add(KeySchemaElement.builder().attributeName("accountId").keyType(KeyType.HASH).build())
            keySchema.add(KeySchemaElement.builder().attributeName("yearMonth").keyType(KeyType.RANGE).build())

            // attributes
            val attributes = ArrayList<AttributeDefinition>()
            attributes.add(AttributeDefinition.builder().attributeName("accountId").attributeType("N").build())
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
        val expressionAttributeValues: MutableMap<String, AttributeValue> = HashMap()
        expressionAttributeValues[":accountId"] = AttributeValue.builder().n(accountId.toString()).build()
        expressionAttributeValues[":invoiceFullNumber"] = AttributeValue.builder().s(number.getFullNumber()).build()
        val query = QueryRequest.builder()
            .keyConditionExpression("accountId = :accountId and invoiceFullNumber = :invoiceFullNumber")
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
        val id = factory.getUniqueIdentifier(request)
        val item = factory.to(request)

        val put = PutItemRequest.builder()
            .tableName(TABLE_NAME)
            .item(item)
            .conditionExpression("attribute_not_exists($id)")
            .build()

        val putResult = dynamoDB.putItem(put)
        LOG.debug("Saved a new request $putResult")
    }

    override fun findLast(accountId: Int, month: Int, year: Int): InvoiceRequest? {
        val expressionAttributeValues: MutableMap<String, AttributeValue> = HashMap()
        expressionAttributeValues[":accountId"] = AttributeValue.builder().n(accountId.toString()).build()
        expressionAttributeValues[":yearMonth"] = AttributeValue.builder().s("$year-$month").build()
        val query = QueryRequest.builder()
            .keyConditionExpression("accountId = :accountId and yearMonth = :yearMonth")
            .expressionAttributeValues(expressionAttributeValues)
            .tableName(TABLE_NAME)
            .build()

        val queryResponse = dynamoDB.query(query)
        if (queryResponse.count() == 0) {
            return null
        }

        val items = queryResponse.items()
        val existingInvoiceRequests = items.map { factory.from(it) }
        val sorted = existingInvoiceRequests.sortedBy { it.getCreationDate() }
        return sorted[sorted.lastIndex]
    }

}