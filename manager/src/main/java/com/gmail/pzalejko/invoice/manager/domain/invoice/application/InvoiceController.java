package com.gmail.pzalejko.invoice.manager.domain.invoice.application;

import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.Invoice;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.InvoiceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class InvoiceController {

    private final InvoiceFactory factory;
    private final InvoiceService service;

    @PostMapping("/v1/invoices")
    NewInvoiceResponseDto create(@RequestBody @Validated NewInvoiceDto dto) {
        Invoice invoice = service.createNew(factory.toInvoice(dto));
        return new NewInvoiceResponseDto(invoice.getId().toString());
    }

    record NewInvoiceResponseDto(@NonNull String id) {

    }
}
