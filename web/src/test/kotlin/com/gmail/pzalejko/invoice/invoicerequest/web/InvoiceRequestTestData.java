package com.gmail.pzalejko.invoice.invoicerequest.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * A factory class for test data.
 */
public class InvoiceRequestTestData {

    @AllArgsConstructor
    @Data
    static class TestRequestDto {
        LocalDate paymentDate;
        LocalDate saleDate;
        LocalDate creationDate;
        TestInvoiceClientDto client;
        List<TestInvoiceItemDto> items;
    }

    @AllArgsConstructor
    @Data
    static class TestInvoiceClientDto {
        String name;
        TestInvoiceClientAddressDto address;
        String taxId;
    }

    @AllArgsConstructor
    @Data
    static class TestInvoiceClientAddressDto {
        String street;
        String number;
        String city;
    }

    @AllArgsConstructor
    @Data
    static class TestInvoiceItemDto {
        String name;
        int count;
        String unit;
        TestInvoiceItemPriceDto pricePerOne;
    }

    @AllArgsConstructor
    @Data
    static class TestInvoiceItemPriceDto {
        double value;
        double taxPercentage;
        String currency;
    }

    @SneakyThrows
    public static String getInvoiceRequest() {
        return getInvoiceRequest(LocalDate.now());
    }

    @SneakyThrows
    public static String getInvoiceRequest(LocalDate date) {
        return getInvoiceRequest(date, date, date);
    }

    @SneakyThrows
    public static String getInvoiceRequest(LocalDate paymentDate, LocalDate saleDate, LocalDate creationDate) {
        var objectMapper = getObjectMapper();

        var clientAddress = new TestInvoiceClientAddressDto("foo", "0", "ZG");
        var client = new TestInvoiceClientDto("Pawel", clientAddress, "0123456789");
        var items = List.of(
                new TestInvoiceItemDto("pc", 1, "COUNT", new TestInvoiceItemPriceDto(1, 1, "PLN"))
        );
        var request = new TestRequestDto(paymentDate, saleDate, creationDate, client, items);

        return objectMapper.writeValueAsString(request);
    }


    @NotNull
    private static ObjectMapper getObjectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
