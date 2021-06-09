package com.gmail.pzalejko.invoicegenerator.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.gmail.pzalejko.invoicegenerator.model.InvoiceInput;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@ToString
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InvoiceInputFactory {

    public static InvoiceInput create(JsonNode node) {
        JsonNode records = node.get("Records");
        if (!(records instanceof ArrayNode)) {
            throw new IllegalArgumentException("Invalid input, expected an array of records");
        }

        return new InvoiceInput(0, "", List.of(), "", LocalDate.now(), LocalDate.now(), LocalDate.now());
    }

    private final JsonNode record;
}
