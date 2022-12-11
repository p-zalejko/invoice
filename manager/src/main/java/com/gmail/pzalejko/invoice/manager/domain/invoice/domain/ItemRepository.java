package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;


import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Item;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.ItemId;

import java.util.Optional;

public interface ItemRepository {

    Optional<Item> findById(ItemId id);

    Item save(Item item);
}
