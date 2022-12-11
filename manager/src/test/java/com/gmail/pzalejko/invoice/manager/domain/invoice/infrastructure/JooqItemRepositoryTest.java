package com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure;

import com.gmail.pzalejko.invoice.manager.TestContainerBasedTest;
import com.gmail.pzalejko.invoice.manager.db.tables.Item;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.ItemRepository;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.ItemId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JooqItemRepositoryTest extends TestContainerBasedTest {

    ItemRepository itemRepository;

    @BeforeEach
    public void setup() {
        itemRepository = new JooqItemRepository(jooqConfig.dsl());
    }

    @AfterEach
    public void tearDown() {
        clearTables(Item.ITEM);
    }

    @Test
    public void shouldCreateNew() {
        // given
        var item = TestDataFactory.newItem();

        // when
        var id = itemRepository.save(item).id();

        // then
        assertThat(id).isNotNull();
    }

    @Test
    public void shouldFoundExistingCompany() {
        // given
        var item = TestDataFactory.newItem();

        // when
        var id = itemRepository.save(item).id();

        // then
        assertThat(itemRepository.findById(id)).isPresent();
    }

    @Test
    public void shouldNotFoundNotExistingCompany() {
        assertThat(itemRepository.findById(new ItemId(1_000))).isEmpty();
    }
}
