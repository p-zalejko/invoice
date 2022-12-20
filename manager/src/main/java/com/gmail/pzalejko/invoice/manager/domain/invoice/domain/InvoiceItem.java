package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class InvoiceItem {

    private InvoiceItemId id;
    private int quantity;
    // TODO: it should  be a copy of the item. Changing item details should not impact already crated invoices
    private @NonNull Item item;

}
