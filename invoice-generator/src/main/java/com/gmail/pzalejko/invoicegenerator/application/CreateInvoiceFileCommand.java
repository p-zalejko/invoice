package com.gmail.pzalejko.invoicegenerator.application;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CreateInvoiceFileCommand {

    private Record[] Records;

    @ToString
    @Data
    public static class Record {
        private String eventID;
        private String eventName;

    }
}
