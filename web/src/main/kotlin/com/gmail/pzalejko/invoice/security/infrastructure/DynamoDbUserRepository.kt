package com.gmail.pzalejko.invoice.security.infrastructure

import com.gmail.pzalejko.invoice.security.model.InvoiceUser
import io.quarkus.security.credential.PasswordCredential
import org.jboss.logging.Logger
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*
import java.util.*
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Default
import javax.inject.Inject

@ApplicationScoped
class DynamoDbUserRepository {

    private val LOG: Logger = Logger.getLogger(DynamoDbUserRepository::class.java)

    companion object {
        const val TABLE_NAME = "InvoiceApp_Users"
    }

    @Inject
    @field: Default
    lateinit var dynamoDB: DynamoDbClient

    @Inject
    @field: Default
    lateinit var factory: UserFactory

    @Inject
    @field: Default
    lateinit var passwordFactory: PasswordFactory

    @PostConstruct
    fun init() {
        val tables = dynamoDB.listTables()
        if (!tables.tableNames().contains(TABLE_NAME)) {
            LOG.info("Table $TABLE_NAME does not exist")

            // key
            val keySchema = ArrayList<KeySchemaElement>()
            keySchema.add(KeySchemaElement.builder().attributeName("username").keyType(KeyType.HASH).build())
            keySchema.add(KeySchemaElement.builder().attributeName("accountId").keyType(KeyType.RANGE).build())
            // attributes
            val attributes = ArrayList<AttributeDefinition>()
            attributes.add(AttributeDefinition.builder().attributeName("username").attributeType("S").build())
            attributes.add(AttributeDefinition.builder().attributeName("accountId").attributeType("N").build())

            val throughput = ProvisionedThroughput.builder()
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
            LOG.info("Table $createTable created")
        }
    }

    fun createUser(username: String, password: CharArray, accountId: Int, roles: Set<String>) {
        val newUser = factory.to(username, password, accountId, roles)
        val put = PutItemRequest.builder()
            .tableName(TABLE_NAME)
            .item(newUser)
            .conditionExpression("attribute_not_exists(username)")
            .build()

        val putResult = dynamoDB.putItem(put)
        LOG.debug("Saved a new request $putResult")
    }

    fun findUser(username: String, password: CharArray): InvoiceUser? {
        val expressionAttributeValues: MutableMap<String, AttributeValue> = HashMap()
        expressionAttributeValues[":username"] = AttributeValue.builder().s(username).build()
        val query = QueryRequest.builder()
            .keyConditionExpression("username = :username")
            .expressionAttributeValues(expressionAttributeValues)
            .tableName(TABLE_NAME)
            .build()

        val queryResponse = dynamoDB.query(query)
        if (queryResponse.count() == 0) {
            LOG.error("Someone tries to log in to not existing account: $username")
            return null
        }

        if (queryResponse.count() != 1) {
            throw IllegalStateException("More than one invoice exist for the given number $username")
        }

        val user = factory.from(queryResponse.items().first())
        val credential = user.getCredential(PasswordCredential::class.java)

        val isValidPwd = passwordFactory.verifyPassword(password, credential!!.password!!)
        if (isValidPwd) {
            return user
        }

        LOG.error("User provided an invalid password for $user account")
        return null
    }
}