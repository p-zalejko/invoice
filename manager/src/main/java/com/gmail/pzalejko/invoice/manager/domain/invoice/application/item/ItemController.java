package com.gmail.pzalejko.invoice.manager.domain.invoice.application.item;

import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class ItemController {

    private final ItemFactory factory;
    private final InvoiceService service;

    @PostMapping("/v1/items")
    NewItemResponseDto create(@RequestBody @Validated NewItemDto dto) {
        var item = factory.toItem(dto);
        return new NewItemResponseDto(service.createNew(item).id().toString());
    }
}
