package com.gmail.pzalejko.invoice.manager.domain.invoice.application;

import com.gmail.pzalejko.invoice.manager.domain.common.Unit;
import lombok.NonNull;

record NewItemDto(@NonNull String name, @NonNull String description, @NonNull Unit unit, double price, int vat) {

}