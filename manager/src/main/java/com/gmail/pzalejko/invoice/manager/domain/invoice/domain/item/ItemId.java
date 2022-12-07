package com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item;

public record ItemId(int value) {

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
