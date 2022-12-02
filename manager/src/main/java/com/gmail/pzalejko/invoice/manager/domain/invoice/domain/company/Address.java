package com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company;

import com.gmail.pzalejko.invoice.manager.domain.common.ValueObject;

public record Address(String street, String number, String zip, String city, String country) implements ValueObject {
}
