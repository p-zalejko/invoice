package com.gmail.pzalejko.invoice.manager.domain.item.domain;

import com.gmail.pzalejko.invoice.manager.domain.common.Price;
import lombok.NonNull;

public record Item(@NonNull ItemId id,
                   @NonNull Name name,
                   @NonNull Description description,
                   @NonNull Price price) {

}
