package com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure;

import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.CompanyRepository;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.InvoiceRepository;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.ItemRepository;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    CompanyRepository companyRepository(DefaultDSLContext context) {
        return new JooqCompanyRepository(context);
    }

    @Bean
    ItemRepository itemRepository(DefaultDSLContext context) {
        return new JooqItemRepository(context);
    }

    @Bean
    InvoiceRepository invoiceRepository(DefaultDSLContext context) {
        return new JooqInvoiceRepository(context);
    }

}
