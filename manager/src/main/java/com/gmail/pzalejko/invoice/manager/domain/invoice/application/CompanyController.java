package com.gmail.pzalejko.invoice.manager.domain.invoice.application;

import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.ItemService;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class CompanyController {

    private final CompanyFactory factory;
    private final ItemService service;

    @PostMapping("/v1/sellerCompanies")
    NewCompanyResponseDto create(@RequestBody @Validated NewSellerCompanyDto dto) {
        Company company = service.createNew(factory.toSellerCompany(dto));
        return new NewCompanyResponseDto(company.getId().toString());
    }

    @PostMapping("/v1/billToCompanies")
    NewCompanyResponseDto createBillTo(@RequestBody @Validated NewBillToCompanyDto dto) {
        Company company = service.createNew(factory.toBillCompany(dto));
        return new NewCompanyResponseDto(company.getId().toString());
    }

    record NewCompanyResponseDto(@NonNull String id) {

    }
}
