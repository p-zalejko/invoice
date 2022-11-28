package com.gmail.pzalejko.invoice.manager.domain.common;

import com.google.common.base.Preconditions;
import lombok.NonNull;

import java.util.Locale;

public record Price(double value, @NonNull Currency currency, @NonNull VatPercentage vatPercentage) {

    public Price {
        Preconditions.checkArgument(value > 0);
    }
}
