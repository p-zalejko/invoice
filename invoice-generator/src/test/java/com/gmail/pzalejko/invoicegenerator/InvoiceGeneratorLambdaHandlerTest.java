package com.gmail.pzalejko.invoicegenerator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.amazon.lambda.test.LambdaClient;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@QuarkusTest
public class InvoiceGeneratorLambdaHandlerTest {

    @Test
    public void testSimpleLambdaSuccess() throws Exception {
        try (InputStream in = InvoiceGeneratorLambdaHandlerTest.class.getResourceAsStream("/payload.json");) {
            String jsonString = readStream(in);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jsonString);

            InvoiceRequestOutput out = LambdaClient.invoke(InvoiceRequestOutput.class, node);
            Assertions.assertNotNull(out);
        }


    }

    private static String readStream(InputStream in) throws IOException {
        byte[] data = new byte[1024];
        int r;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while ((r = in.read(data)) > 0) {
            out.write(data, 0, r);
        }
        return out.toString(StandardCharsets.UTF_8);
    }
}
