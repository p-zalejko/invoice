package com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure;

import com.gmail.pzalejko.invoice.manager.TestContainerBasedTest;
import com.gmail.pzalejko.invoice.manager.db.tables.Company;
import com.gmail.pzalejko.invoice.manager.db.tables.CompanyAddress;
import com.gmail.pzalejko.invoice.manager.db.tables.Invoice;
import com.gmail.pzalejko.invoice.manager.db.tables.Invoiceitem;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.CompanyRepository;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.InvoiceRepository;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.ItemRepository;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JooqInvoiceRepositoryTest extends TestContainerBasedTest {

    InvoiceRepository invoiceRepository;
    CompanyRepository companyRepository;
    ItemRepository itemRepository;

    com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company companyA;
    com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company companyB;
    Item item;

    @BeforeEach
    public void setup() {
        invoiceRepository = new JooqInvoiceRepository(jooqConfig.dsl());
        companyRepository = new JooqCompanyRepository(jooqConfig.dsl());
        itemRepository = new JooqItemRepository(jooqConfig.dsl());

        companyA = companyRepository.save(TestDataFactory.newCompany());
        companyB = companyRepository.save(TestDataFactory.newCompany());
        item = itemRepository.save(TestDataFactory.newItem());
    }

    @AfterEach
    public void tearDown() {
        clearTables(Invoiceitem.INVOICEITEM, Invoice.INVOICE, Company.COMPANY, CompanyAddress.COMPANY_ADDRESS);
    }


    @Test
    public void shouldCreateNew() {
        // given
        var invoice = TestDataFactory.newInvoice(companyA, companyB, List.of(item));

        // when
        var id = invoiceRepository.save(invoice).getId();

        // then
        assertThat(id).isNotNull();
    }

    @Test
    public void shouldFoundExisting() {
        // given
        var invoice = TestDataFactory.newInvoice(companyA, companyB, List.of(item));
        var id = invoiceRepository.save(invoice).getId();
        // when
        assertThat(invoiceRepository.findById(id)).isPresent();

    }

}
