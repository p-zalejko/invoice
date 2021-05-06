package com.gmail.pzalejko.invoicegenerator;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class InvoiceRequestInput {

    private Record[] Records;

    @ToString
    @Data
    public static class Record {
        private String eventID;
        private String eventName;

    }
}
