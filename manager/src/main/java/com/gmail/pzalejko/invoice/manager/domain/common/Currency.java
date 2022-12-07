package com.gmail.pzalejko.invoice.manager.domain.common;

import lombok.NonNull;

import java.util.Locale;

public record Currency(@NonNull java.util.Currency value) {

    public static Currency from(@NonNull String code) {
        return new Currency(java.util.Currency.getInstance(code));
    }

    public static Currency PLN = new Currency(java.util.Currency.getInstance(new Locale("pl", "PL")));


    public String currencyCode() {
        return value.getCurrencyCode();
    }
}
