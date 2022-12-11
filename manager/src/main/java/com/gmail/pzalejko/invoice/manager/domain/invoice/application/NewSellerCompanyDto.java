package com.gmail.pzalejko.invoice.manager.domain.invoice.application;

import lombok.NonNull;

record NewSellerCompanyDto(@NonNull String name,
                           @NonNull String street,
                           @NonNull String number,
                           @NonNull String zip,
                           @NonNull String city,
                           @NonNull String country,
                           @NonNull String companyTaxId,
                           @NonNull String bankAccountNumber) {
}
