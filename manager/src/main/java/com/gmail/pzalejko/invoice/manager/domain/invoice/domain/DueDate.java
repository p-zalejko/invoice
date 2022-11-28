package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.common.ValueObject;
import lombok.NonNull;

import java.time.LocalDate;

record DueDate(@NonNull LocalDate localDate) implements ValueObject {

}
