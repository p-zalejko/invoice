package com.gmail.pzalejko.generator.invoiceinput.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Parses a new event sent by DynamoDB after saving new record. The event itself is a JSON object, containing the
 * full payload of a newly crested record.
 * <p>
 * For more information read https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Streams.Lambda.html
 */
@ApplicationScoped
public class InvoiceInputFactory {

    private ObjectMapper mapper;

    @PostConstruct
    void init() {
        mapper = new ObjectMapper();
    }

    @SneakyThrows
    public InvoiceInput create(JsonNode node) {
        JsonNode records = node.get("Records");
        if (!(records instanceof ArrayNode)) {
            throw new IllegalArgumentException("Invalid input, expected an array of records");
        }

        JsonNode jsonNode = records.get(0).get("dynamodb").get("NewImage");
        InvoiceInputInternal invoiceInputInternal = mapper.treeToValue(jsonNode, InvoiceInputInternal.class);

        return new InvoiceInput(
                invoiceInputInternal.sellerDetails,
                invoiceInputInternal.clientDetails,
                invoiceInputInternal.invoiceFullNumber,
                invoiceInputInternal.items,
                invoiceInputInternal.dueDate,
                invoiceInputInternal.saleDate,
                invoiceInputInternal.creationDate
        );
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class InvoiceInputInternal {

        long accountId;
        String invoiceFullNumber;
        ClientInfo clientDetails;
        SellerInfo sellerDetails;
        LocalDate dueDate;
        LocalDate saleDate;
        LocalDate creationDate;

        private List<InvoiceInput.Item> items;

        @JsonProperty("accountId")
        void setAccountId(Map<String, String> node) {
            this.accountId = Long.parseLong(node.get("N"));
        }

        @JsonProperty("invoiceFullNumber")
        void setInvoiceFullNumber(Map<String, String> node) {
            this.invoiceFullNumber = node.get("S");
        }

        @JsonProperty("client")
        void setClientDetails(Map<String, Map<String, Map<String, String>>> node) {
            var clientMap = node.get("M");

            var name = clientMap.get("clientName").get("S");
            var taxId = clientMap.get("clientTaxId").get("S");
            var address = String.format("%s\n%s\n%s",
                    clientMap.get("clientAddressCity").get("S"),
                    clientMap.get("clientAddressStreet").get("S"),
                    clientMap.get("clientAddressNumber").get("S"));

            this.clientDetails = new ClientInfo(name, address, taxId);
        }

        @JsonProperty("seller")
        void setSellerDetails(Map<String, Map<String, Map<String, String>>> node) {
            var clientMap = node.get("M");

            var name = clientMap.get("sellerName").get("S");
            var id = Long.parseLong(clientMap.get("sellerId").get("N"));
            var taxId = clientMap.get("sellerTaxId").get("S");
            var bankNumber = clientMap.get("sellerBankAccount").get("S");
            var address = String.format("%s\n%s\n%s",
                    clientMap.get("sellerAddressCity").get("S"),
                    clientMap.get("sellerAddressStreet").get("S"),
                    clientMap.get("sellerAddressNumber").get("S"));

            this.sellerDetails = new SellerInfo(id, name, address, bankNumber, taxId);
        }

        @JsonProperty("paymentDate")
        void setDueDate(Map<String, String> node) {
            this.dueDate = LocalDate.parse(node.get("S"));
        }

        @JsonProperty("creationDate")
        void setCreationDate(Map<String, String> node) {
            this.creationDate = LocalDate.parse(node.get("S"));
        }

        @JsonProperty("saleDate")
        void setSaleDate(Map<String, String> node) {
            this.saleDate = LocalDate.parse(node.get("S"));
        }

        @JsonProperty("items")
        void setItems(Map<String, List<Map<String, Map<String, Map<String, String>>>>> node) {
            this.items = node.get("L")
                    .stream()
                    .map(this::parseItem)
                    .collect(Collectors.toList());
        }

        private InvoiceInput.Item parseItem(Map<String, Map<String, Map<String, String>>> i) {
            var map = i.get("M");

            var price = new InvoiceInput.Price(
                    Double.parseDouble(map.get("priceValue").get("N")),
                    Double.parseDouble(map.get("priceTax").get("N")),
                    Currency.getInstance(map.get("priceCurrency").get("S"))
            );

            return new InvoiceInput.Item(
                    map.get("name").get("S"),
                    Long.parseLong(map.get("count").get("N")),
                    map.get("unit").get("S"),
                    price);
        }
    }
}