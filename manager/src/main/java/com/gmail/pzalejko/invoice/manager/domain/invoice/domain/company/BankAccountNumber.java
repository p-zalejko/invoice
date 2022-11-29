package com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company;

import com.gmail.pzalejko.invoice.manager.domain.common.ValueObject;
import com.google.common.base.Preconditions;
import lombok.NonNull;

public record BankAccountNumber(@NonNull String value) implements ValueObject {

    public static final BankAccountNumber MISSING = new BankAccountNumber("0".repeat(26));

    public BankAccountNumber {
        Preconditions.checkArgument(value.length() == 26, "Bank account has an invalid length");
    }
}
