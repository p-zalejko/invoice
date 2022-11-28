package com.gmail.pzalejko.invoice.manager.domain.item.domain;

import com.gmail.pzalejko.invoice.manager.domain.common.Price;
import com.gmail.pzalejko.invoice.manager.domain.common.Unit;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class Item {

    private ItemId id;
    private final @NonNull Name name;
    private final @NonNull Description description;
    private final @NonNull VatPercentage vatPercentage;
    private final @NonNull Unit unit;
    private final @NonNull Price price;
}
