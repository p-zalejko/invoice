package com.gmail.pzalejko.invoice.manager.domain.invoice.application.company;

import lombok.NonNull;

record NewBillToCompanyDto(@NonNull String name,
                           @NonNull String street,
                           @NonNull String number,
                           @NonNull String zip,
                           @NonNull String city,
                           @NonNull String country,
                           @NonNull String companyTaxId) {
}
