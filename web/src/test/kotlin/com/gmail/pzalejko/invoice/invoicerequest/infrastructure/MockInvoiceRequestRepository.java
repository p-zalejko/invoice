package com.gmail.pzalejko.invoice.invoicerequest.infrastructure;

import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequest;
import com.gmail.pzalejko.invoice.invoicerequest.model.InvoiceRequestRepository;
import com.gmail.pzalejko.invoice.model.InvoiceNumber;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

/**
 * a mock implementation that simulates repository. It uses an in-memory list as a storage.
 *
 * See https://quarkus.io/guides/writing-extensions#how-to-override-a-bean-defined-by-a-libraryquarkus-extension-that-doesnt-use-defaultbean
 */
@Alternative
@Priority(1)
@Singleton
public class MockInvoiceRequestRepository implements InvoiceRequestRepository {
    @Nullable
    @Override
    public InvoiceRequest findByNumber(@NotNull InvoiceNumber number) {
        return null;
    }

    @Override
    public void save(@NotNull InvoiceRequest request) {

    }

    @Nullable
    @Override
    public InvoiceRequest findLast(int month, int year) {
        return null;
    }
}
