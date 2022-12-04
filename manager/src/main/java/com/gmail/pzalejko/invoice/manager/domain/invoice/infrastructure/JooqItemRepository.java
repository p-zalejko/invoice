package com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure;

import com.gmail.pzalejko.invoice.manager.db.enums.ItemUnit;
import com.gmail.pzalejko.invoice.manager.db.tables.Item;
import com.gmail.pzalejko.invoice.manager.db.tables.records.ItemRecord;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.ItemRepository;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.ItemId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import java.math.BigDecimal;

import static com.gmail.pzalejko.invoice.manager.db.tables.Item.ITEM;

@RequiredArgsConstructor
class JooqItemRepository implements ItemRepository {

    @NonNull
    private final DSLContext dsl;

    @Override
    public com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Item save(@NonNull com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Item item) {
        var name = item.name().value();
        var description = item.description().value();
        var currency = item.price().currency().currencyCode();
        var price = BigDecimal.valueOf(item.price().value());
        var var = item.price().vatPercentage().value();
        var unit = ItemUnit.valueOf(item.unit().name());

        ItemRecord itemRecord = dsl
                .insertInto(ITEM,
                        ITEM.NAME,
                        ITEM.DESCRIPTION,
                        ITEM.PRICE_CURRENCY,
                        ITEM.PRICE_VALUE,
                        ITEM.PRICE_VAT,
                        ITEM.UNIT
                )
                .values(
                        name, description, currency, price, var, unit
                )
                .returning()
                .fetchOne();


        return new com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Item(
                new ItemId(itemRecord.getId()),
                item
        );
    }
}
