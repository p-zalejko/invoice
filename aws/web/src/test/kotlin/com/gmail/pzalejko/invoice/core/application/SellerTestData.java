package com.gmail.pzalejko.invoice.core.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

/**
 * A factory class for test data.
 */
public class SellerTestData {

    @AllArgsConstructor
    @Data
    static class TestCreateSellerCommandDto {
        long accountId;
        String name;
        String street;
        String streetNumber;
        String city;
        String taxId;
        String bankAccount;
    }

    @SneakyThrows
    public static String getSeller(long accountId) {
        var item = new TestCreateSellerCommandDto(
                accountId,
                "a_name",
                "str",
                "str0",
                "ZG", "0123456789",
                "01234567890123456789123456"
        );

        return getObjectMapper().writeValueAsString(item);
    }

    @NotNull
    private static ObjectMapper getObjectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
