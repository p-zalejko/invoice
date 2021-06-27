package com.gmail.pzalejko.invoice.core.infrastructure

import com.gmail.pzalejko.invoice.core.model.subject.CannotCreateAccountException
import com.gmail.pzalejko.invoice.core.model.subject.InvoiceSeller
import com.gmail.pzalejko.invoice.core.model.subject.SellerRepository
import org.jboss.logging.Logger
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*
import java.lang.RuntimeException
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Default
import javax.inject.Inject

@ApplicationScoped
class DynamoDBSellerRepository : SellerRepository {

    @Inject
    @field: Default
    lateinit var log: Logger

    companion object {
        const val TABLE_NAME = "InvoiceApp_Sellers"
    }

    @Inject
    @field: Default
    lateinit var dynamoDB: DynamoDbClient

    @Inject
    @field: Default
    lateinit var factory: DynamoDBSellerFactory


    @PostConstruct
    fun init() {
        val tables = dynamoDB.listTables()
        if (!tables.tableNames().contains(TABLE_NAME)) {
            log.info("Table ${TABLE_NAME} does not exist")

            // key
            val keySchema = ArrayList<KeySchemaElement>()
            keySchema.add(
                KeySchemaElement.builder().attributeName("accountId").keyType(KeyType.HASH).build()
            )

            // attributes
            val attributes = ArrayList<AttributeDefinition>()
            attributes.add(
                AttributeDefinition.builder().attributeName("accountId").attributeType("N").build()
            )

            val throughput = ProvisionedThroughput
                .builder()
                .readCapacityUnits(1)
                .writeCapacityUnits(1)
                .build()

            val request = CreateTableRequest.builder()
                .tableName(TABLE_NAME)
                .keySchema(keySchema)
                .attributeDefinitions(attributes)
                .provisionedThroughput(throughput)
                .build()

            val createTable = dynamoDB.createTable(request)
            log.info("Table $createTable created")
        }
    }

    override fun findByAccountId(accountId: Int): InvoiceSeller? {
        val expressionAttributeValues: MutableMap<String, AttributeValue> = HashMap()
        expressionAttributeValues[":accountId"] = AttributeValue.builder().n(accountId.toString()).build()
        val query = QueryRequest.builder()
            .keyConditionExpression("accountId = :accountId")
            .expressionAttributeValues(expressionAttributeValues)
            .tableName(TABLE_NAME)
            .build()

        val queryResponse = dynamoDB.query(query)
        if (queryResponse.count() == 0) {
            return null
        }

        return factory.from(queryResponse.items().first())
    }

    override fun save(seller: InvoiceSeller) {
        val item = factory.to(seller)

        val put = PutItemRequest.builder()
            .tableName(TABLE_NAME)
            .item(item)
            .conditionExpression("attribute_not_exists(accountId)")
            .build()

        try {
            val putResult = dynamoDB.putItem(put)
            log.debug("Saved a new request $putResult")
        } catch (e: ConditionalCheckFailedException) {
            log.error("Could not create a new account", e)
            throw CannotCreateAccountException(e)
        }
    }
}