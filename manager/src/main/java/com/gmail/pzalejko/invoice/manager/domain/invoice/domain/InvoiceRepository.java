package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import java.util.Optional;

public interface InvoiceRepository {

    Invoice save(Invoice invoice);

    Optional<Invoice> findById(InvoiceId id);
}
