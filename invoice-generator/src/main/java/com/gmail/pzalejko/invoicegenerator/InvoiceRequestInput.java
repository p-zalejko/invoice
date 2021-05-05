package com.gmail.pzalejko.invoicegenerator;

public class InvoiceRequestInput {

    private Record[] Records;

    public Record[] getRecords() {
        return Records;
    }

    public void setRecords(Record[] records) {
        Records = records;
    }

    public static class Record {
        private String eventID;

        public String getEventID() {
            return eventID;
        }

        public void setEventID(String eventID) {
            this.eventID = eventID;
        }
    }
}
