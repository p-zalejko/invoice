package com.gmail.pzalejko.invoice.manager.domain.invoice.application.item;

import com.gmail.pzalejko.invoice.manager.domain.common.Currency;
import com.gmail.pzalejko.invoice.manager.domain.common.Price;
import com.gmail.pzalejko.invoice.manager.domain.common.VatPercentage;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Description;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Item;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Name;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
class ItemFactory {

    Item toItem(@NonNull NewItemDto dto) {
        Name name = new Name(dto.name());
        Description description = new Description(dto.description());
        Price price = new Price(dto.price(), Currency.PLN, new VatPercentage(dto.vat()));

        return new Item(null, name, description, dto.unit(), price);
    }
}
