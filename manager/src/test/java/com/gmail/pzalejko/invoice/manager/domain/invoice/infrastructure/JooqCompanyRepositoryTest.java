package com.gmail.pzalejko.invoice.manager.domain.invoice.infrastructure;

import com.gmail.pzalejko.invoice.manager.TestContainerBasedTest;
import com.gmail.pzalejko.invoice.manager.db.tables.Company;
import com.gmail.pzalejko.invoice.manager.db.tables.CompanyAddress;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.CompanyId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JooqCompanyRepositoryTest extends TestContainerBasedTest {

    JooqCompanyRepository jooqCompanyRepository;

    @BeforeEach
    public void setup() {
        jooqCompanyRepository = new JooqCompanyRepository(jooqConfig.dsl());
    }

    @AfterEach
    public void tearDown() {
        clearTables(Company.COMPANY, CompanyAddress.COMPANY_ADDRESS);
    }

    @Test
    public void shouldFoundNotExistingCompany() {
        // given
        var company = TestDataFactory.newCompany();

        // when
        var id = jooqCompanyRepository.save(company).getId();

        // then
        assertThat(jooqCompanyRepository.findById(id)).isPresent();
    }

    @Test
    public void shouldNotFoundNotExistingCompany() {
        assertThat(jooqCompanyRepository.findById(new CompanyId(1_000))).isEmpty();
    }

    @Test
    public void shouldCreateNew() {
        // given
        var company = TestDataFactory.newCompany();

        // when
        var id = jooqCompanyRepository.save(company).getId();

        // then
        assertThat(id).isNotNull();
    }

}
