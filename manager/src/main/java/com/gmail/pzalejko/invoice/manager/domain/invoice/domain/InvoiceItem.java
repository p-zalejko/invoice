package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.common.Price;
import com.gmail.pzalejko.invoice.manager.domain.common.Unit;
import com.gmail.pzalejko.invoice.manager.domain.item.domain.ItemId;
import com.google.common.base.Preconditions;
import lombok.NonNull;

public record InvoiceItem(@NonNull ItemId id,
                          @NonNull String name,
                          int quantity,
                          @NonNull Unit unit,
                          @NonNull Price price) {

    public InvoiceItem {
        Preconditions.checkArgument(quantity > 0);
    }
}
