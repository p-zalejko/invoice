package com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company;

import com.gmail.pzalejko.invoice.manager.domain.common.ValueObject;
import lombok.NonNull;

public record TaxId(@NonNull String value) implements ValueObject {
}
