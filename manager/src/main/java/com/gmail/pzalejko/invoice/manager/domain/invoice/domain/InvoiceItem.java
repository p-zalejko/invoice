package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.item.domain.ItemId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItem {

    private InvoiceItemId id;
    private @NonNull ItemId itemId;
    private int quantity;

}
