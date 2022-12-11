package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.common.ValueObject;
import com.google.common.base.Preconditions;

public record InvoiceNumber(int number, int year, int month) implements ValueObject {

    public InvoiceNumber {
        Preconditions.checkArgument(number > 0);
        Preconditions.checkArgument(year > 0);
        Preconditions.checkArgument(month > 0);
    }

    InvoiceNumber getNext() {
        return new InvoiceNumber(number + 1, month, year);
    }

    @Override
    public String toString() {
        return String.format("%d/%d/%d", number, month, year);
    }
}
