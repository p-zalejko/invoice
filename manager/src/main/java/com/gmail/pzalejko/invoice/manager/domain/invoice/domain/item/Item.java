package com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item;

import com.gmail.pzalejko.invoice.manager.domain.common.Price;
import com.gmail.pzalejko.invoice.manager.domain.common.Unit;
import lombok.NonNull;

public record Item(ItemId id,
                   @NonNull Name name,
                   @NonNull Description description,
                   @NonNull Unit unit,
                   @NonNull Price price) {

    public Item(@NonNull ItemId id, @NonNull Item item) {
        this(id, item.name, item.description, item.unit, item.price);
    }
}
