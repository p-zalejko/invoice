package com.gmail.pzalejko.invoicegenerator;

import io.quarkus.amazon.lambda.test.LambdaClient;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class InvoiceGeneratorLambdaHandlerTest {

    @Test
    public void testSimpleLambdaSuccess() {
        InvoiceRequestInput in = new InvoiceRequestInput();
        in.setRecords(new InvoiceRequestInput.Record[]{});
        InvoiceRequestOutput out = LambdaClient.invoke(InvoiceRequestOutput.class, in);
        Assertions.assertEquals(0, out.getIds().length);
    }

}
