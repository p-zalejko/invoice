package com.gmail.pzalejko.invoice.manager.domain.item.domain;

import com.gmail.pzalejko.invoice.manager.domain.common.ValueObject;
import com.google.common.base.Preconditions;

public record VatPercentage(int value) implements ValueObject {

    public VatPercentage {
        Preconditions.checkArgument(value >= 0 && value <= 100);
    }
}
