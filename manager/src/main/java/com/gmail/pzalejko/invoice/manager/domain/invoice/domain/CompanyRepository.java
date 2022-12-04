package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.CompanyId;

import java.util.Optional;

public interface CompanyRepository {

    Company save(Company company);

    Optional<Company> findById(CompanyId id);
}
