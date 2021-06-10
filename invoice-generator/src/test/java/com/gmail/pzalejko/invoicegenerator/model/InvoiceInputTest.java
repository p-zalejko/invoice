package com.gmail.pzalejko.invoicegenerator.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InvoiceInputTest {

    @Test
    void totalPriceValue_forOne() {
        InvoiceInput invoiceInput = InvoiceInputTestData.testData(1);
        assertThat(10d).isEqualTo(invoiceInput.totalPriceValue());
    }

    @Test
    void totalPriceValue_forMany() {
        InvoiceInput invoiceInput = InvoiceInputTestData.testData(5);
        assertThat(150d).isEqualTo(invoiceInput.totalPriceValue());
    }
}