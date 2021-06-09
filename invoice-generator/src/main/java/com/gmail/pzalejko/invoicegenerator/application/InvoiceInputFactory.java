package com.gmail.pzalejko.invoicegenerator.application;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.gmail.pzalejko.invoicegenerator.model.InvoiceInput;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@ToString
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InvoiceInputFactory {

    @SneakyThrows
    public static InvoiceInput create(JsonNode node) {
        JsonNode records = node.get("Records");
        if (!(records instanceof ArrayNode)) {
            throw new IllegalArgumentException("Invalid input, expected an array of records");
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = records.get(0).get("dynamodb").get("NewImage");
        InvoiceInputInternal invoiceInputInternal = mapper.treeToValue(jsonNode, InvoiceInputInternal.class);

        return new InvoiceInput(
                invoiceInputInternal.accountId,
                invoiceInputInternal.invoiceFullNumber,
                List.of(),
                "",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now()
        );
    }

    private final JsonNode record;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class InvoiceInputInternal {

        long accountId;
        String invoiceFullNumber;

        @JsonProperty("accountId")
        void setAccountId(Map<String, String> node) {
            this.accountId = Long.parseLong(node.get("N"));
        }

        @JsonProperty("invoiceFullNumber")
        void setInvoiceFullNumber(Map<String, String> node) {
            this.invoiceFullNumber = node.get("S");
        }

    }
}
