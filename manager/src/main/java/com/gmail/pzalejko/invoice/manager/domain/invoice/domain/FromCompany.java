package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.company.domain.CompanyId;
import lombok.NonNull;

public record FromCompany(@NonNull CompanyId companyId,
                          @NonNull String taxId,
                          @NonNull String name,
                          @NonNull String address,
                          @NonNull String accountNumber) {
}
