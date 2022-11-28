package com.gmail.pzalejko.invoice.manager.domain.company.domain;

import com.gmail.pzalejko.invoice.manager.domain.common.ValueObject;

public record Address(String street, String number, String zipCode, String city, String country) implements ValueObject {
}
