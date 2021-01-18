package com.gmail.pzalejko.invoice.invoicerequest.infrastructure;

import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequest;
import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequestRepository;
import com.gmail.pzalejko.invoice.model.InvoiceNumber;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * a mock implementation that simulates repository. It uses an in-memory list as a storage.
 * <p>
 * See https://quarkus.io/guides/writing-extensions#how-to-override-a-bean-defined-by-a-libraryquarkus-extension-that-doesnt-use-defaultbean
 */
//@Alternative
//@Priority(1)
//@Singleton
public class MockInvoiceRequestRepository implements InvoiceRequestRepository {

    private final List<InvoiceRequest> invoiceRequests = new ArrayList<>();

    @Nullable
    @Override
    public InvoiceRequest findByNumber(int accountId, @NotNull InvoiceNumber number) {
        return null;
    }

    @Override
    public void save(@NotNull InvoiceRequest request) {
        invoiceRequests.add(request);
    }

    @Nullable
    @Override
    public InvoiceRequest findLast(int accountId, int month, int year) {
        var perMonth = invoiceRequests.stream()
                .filter(i -> i.getCreationDate().getDate().getYear() == year &&
                        i.getCreationDate().getDate().getMonthValue() == month)
                .sorted(Comparator.comparing(o -> o.getCreationDate().getDate()))
                .collect(Collectors.toCollection(LinkedList::new));

        return perMonth.isEmpty() ? null : perMonth.getLast();
    }

    public void clear() {
        invoiceRequests.clear();
    }
}
