package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.common.ValueObject;

public record InvoiceId(int value) implements ValueObject {

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
