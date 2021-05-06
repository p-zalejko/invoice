package com.gmail.pzalejko.invoicegenerator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.quarkus.amazon.lambda.test.LambdaClient;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class InvoiceGeneratorLambdaHandlerTest {

    @Test
    public void testSimpleLambdaSuccess() {
        JsonNode in = JsonNodeFactory.instance.missingNode();
        InvoiceRequestOutput out = LambdaClient.invoke(InvoiceRequestOutput.class, in);
        Assertions.assertEquals(0, out.getIds().length);
    }

}
