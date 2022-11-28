package com.gmail.pzalejko.invoice.manager.domain.common;

import com.google.common.base.Preconditions;

public record VatPercentage(int value) implements ValueObject {

    public VatPercentage {
        Preconditions.checkArgument(value >= 0 && value <= 100);
    }
}
