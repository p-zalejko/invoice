package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.common.ValueObject;
import lombok.NonNull;

import java.time.LocalDate;

public record IssueDate(@NonNull LocalDate value) implements ValueObject {
}
