package com.gmail.pzalejko.invoiceinput;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.pzalejko.invoiceinput.application.Output;
import io.quarkus.amazon.lambda.test.LambdaClient;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@QuarkusTest
public class InvoiceInputGeneratorLambdaHandlerTest {

    private JsonNode samplePayload;

    @BeforeEach
    void setUp() throws IOException {
        try (var in = InvoiceInputGeneratorLambdaHandlerTest.class.getResourceAsStream("/payload.json")) {
            var asString = IOUtils.toString(in, StandardCharsets.UTF_8);
            var mapper = new ObjectMapper();
            samplePayload = mapper.readTree(asString);
        }
    }

    @Test
    public void testSimpleLambdaSuccess() {
        Output out = LambdaClient.invoke(Output.class, samplePayload);
        Assertions.assertNotNull(out);
    }
}
