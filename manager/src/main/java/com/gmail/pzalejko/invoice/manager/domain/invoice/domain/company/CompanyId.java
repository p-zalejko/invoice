package com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company;

public record CompanyId(int value) {

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
