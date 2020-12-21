package com.gmail.pzalejko.invoice.invoicerequest.infrastructure;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.GenericContainer;

import java.util.Collections;
import java.util.Map;

/**
 * A special binder that allows for launching dynamoDB (testContainers) and register a port within the quarkus. As a result,
 * before launching a quarkus app the port will be already configured.
 *
 * This class is used in the following way:
 *
 * <blockquote><pre>
 *     @QuarkusTest
 *     @QuarkusTestResource(DynamoDbResource.class)
 *     public class MyTestClass  { ... }
 * </pre></blockquote>
 * <p>
 *
 * Sample: https://github.com/quarkusio/quarkus-quickstarts/blob/master/kafka-quickstart/src/test/java/org/acme/kafka/PriceResourceTest.java
 */
public class DynamoDbResource implements QuarkusTestResourceLifecycleManager {

    public static GenericContainer dynamoDBLocal =
            new GenericContainer("amazon/dynamodb-local:1.11.477")
                    .withExposedPorts(8000);


    @Override
    public Map<String, String> start() {
        dynamoDBLocal.start();
        return Collections.singletonMap("quarkus.dynamodb.endpoint-override", "http://localhost:" + dynamoDBLocal.getFirstMappedPort());
    }

    @Override
    public void stop() {
        dynamoDBLocal.stop();
    }
}
