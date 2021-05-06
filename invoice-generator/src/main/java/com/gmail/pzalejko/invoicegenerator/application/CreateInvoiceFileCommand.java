package com.gmail.pzalejko.invoicegenerator.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.*;

@ToString
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateInvoiceFileCommand {

    public static CreateInvoiceFileCommand create(JsonNode node) {
        JsonNode records = node.get("Records");
        if (!(records instanceof ArrayNode)) {
            throw new IllegalArgumentException("Invalid input, expected an array of records");
        }

        return new CreateInvoiceFileCommand(records.get(0));
    }

    private final JsonNode record;
}
