package com.gmail.pzalejko.invoice.manager.domain.invoice.application;

import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.*;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.CompanyId;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Item;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.ItemId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class InvoiceFactory {

    private final InvoiceService service;

    Invoice toInvoice(@NonNull NewInvoiceDto dto) {
        InvoiceNumber number = getNextNumber();
        IssueDate issueDate = new IssueDate(dto.issueDate());
        DueDate dueDate = new DueDate(dto.dueDate());
        Company fromCompany = getCompanyById(new CompanyId(dto.fromCompany()));
        Company billToCompany = getCompanyById(new CompanyId(dto.billToCompany()));
        List<InvoiceItem> items = toInvoiceItems(dto.items());

        return new Invoice(null, number, issueDate, dueDate, fromCompany, billToCompany, items);
    }

    private InvoiceNumber getNextNumber() {
        return service.getNextNumber();
    }

    private Company getCompanyById(CompanyId id) {
        return service.getCompany(id);
    }

    private List<InvoiceItem> toInvoiceItems(List<NewInvoiceDto.NewInvoiceItemsDto> items) {
        return items.stream()
                .map(i -> {
                    var item = service.getItem(new ItemId(i.itemId()));
                    return new InvoiceItem(null, i.quantity(), item);
                })
                .toList();
    }
}
