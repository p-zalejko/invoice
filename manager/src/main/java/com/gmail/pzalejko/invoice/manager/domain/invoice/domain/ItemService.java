package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Item;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository repository;

    public Item createNew(@NonNull Item item) {
        return repository.save(item);
    }
}
