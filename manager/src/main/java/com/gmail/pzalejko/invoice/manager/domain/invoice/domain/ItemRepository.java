package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;


import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Item;

public interface ItemRepository {

    Item save(Item item);
}
