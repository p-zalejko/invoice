package com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item;

import com.gmail.pzalejko.invoice.manager.domain.common.ValueObject;
import lombok.NonNull;

public record Description(@NonNull String value) implements ValueObject {
}
