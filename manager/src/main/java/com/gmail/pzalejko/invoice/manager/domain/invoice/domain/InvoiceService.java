package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.CompanyId;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Item;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.ItemId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final ItemRepository itemRepository;
    private final CompanyRepository companyRepository;
    private final InvoiceRepository invoiceRepository;

    public Item createNew(@NonNull Item item) {
        return itemRepository.save(item);
    }

    public Company createNew(@NonNull Company company) {
        return companyRepository.save(company);
    }

    public Invoice createNew(@NonNull Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Company getCompany(@NonNull CompanyId id) {
        return companyRepository.findById(id).orElseThrow();
    }

    public Item getItem(@NonNull ItemId id) {
        return itemRepository.findById(id).orElseThrow();
    }

    public InvoiceNumber getNextNumber() {
        return null;
    }
}
