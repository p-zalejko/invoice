package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.common.ValueObject;
import com.google.common.base.Preconditions;

record InvoiceNumber(long number, int year, int month) implements ValueObject {

    public InvoiceNumber {
        Preconditions.checkArgument(number > 0);
        Preconditions.checkArgument(year > 0);
        Preconditions.checkArgument(month > 0);
    }
}
