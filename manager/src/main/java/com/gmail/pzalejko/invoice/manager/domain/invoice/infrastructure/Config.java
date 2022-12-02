package com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure;

import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.CompanyRepository;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    CompanyRepository companyRepository(DefaultDSLContext context) {
        return new JooqCompanyRepository(context);
    }
}
