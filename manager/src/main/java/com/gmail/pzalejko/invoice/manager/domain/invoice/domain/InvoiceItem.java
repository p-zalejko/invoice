package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.common.Price;
import com.gmail.pzalejko.invoice.manager.domain.common.Unit;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItem {

    private InvoiceItemId id;
    private int quantity;
    private @NonNull Item itemId;

    // state from thr Item from the moment of creating the invoice request
    private @NonNull String name;
    private @NonNull Unit unit;
    private @NonNull Price price;

}
