package com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure;

import com.gmail.pzalejko.invoice.manager.TestContainerBasedTest;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.CompanyId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JooqCompanyRepositoryTest extends TestContainerBasedTest {

    JooqCompanyRepository jooqCompanyRepository;

    @BeforeEach
    public void setup() {
        jooqCompanyRepository = new JooqCompanyRepository(jooqConfig.dsl());
    }

    @Test
    public void shouldNotFoundNotExistingCompany() {
        assertThat(jooqCompanyRepository.findById(new CompanyId(1))).isPresent();
    }

}
