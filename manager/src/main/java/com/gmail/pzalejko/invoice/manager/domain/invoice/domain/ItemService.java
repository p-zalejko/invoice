package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.company.Company;
import com.gmail.pzalejko.invoice.manager.domain.invoice.domain.item.Item;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CompanyRepository companyRepository;

    public Item createNew(@NonNull Item item) {
        return itemRepository.save(item);
    }

    public Company createNew(@NonNull Company company) {
        return companyRepository.save(company);
    }
}
