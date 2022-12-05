package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.common.ValueObject;
import com.google.common.base.Preconditions;

public record InvoiceNumber(String value) implements ValueObject {

    public InvoiceNumber(int number, int year, int month) {
        this(String.format("%d/%d/%d",
                        validateNumber(number),
                        validateNumber(month),
                        validateNumber(year)
                )
        );
    }

    private static int validateNumber(int num) {
        Preconditions.checkArgument(num > 0);
        return num;
    }
}
