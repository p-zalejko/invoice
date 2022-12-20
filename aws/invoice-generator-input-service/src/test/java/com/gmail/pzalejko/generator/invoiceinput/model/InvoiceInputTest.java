package com.gmail.pzalejko.generator.invoiceinput.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InvoiceInputTest {

    @Test
    void totalPriceValue_forOne() {
        InvoiceInput invoiceInput = InvoiceInputTestData.testData(1);
        assertThat(10d).isEqualTo(invoiceInput.totalPriceValue());
    }

    @Test
    void totalTaxValue_forOne() {
        InvoiceInput invoiceInput = InvoiceInputTestData.testData(1);
        assertThat(1d).isEqualTo(invoiceInput.totalTaxValue());
    }

    @Test
    void totalPriceValue_forMany() {
        InvoiceInput invoiceInput = InvoiceInputTestData.testData(5);
        assertThat(150d).isEqualTo(invoiceInput.totalPriceValue());
    }

    @Test
    void totalTaxValue_forMany() {
        InvoiceInput invoiceInput = InvoiceInputTestData.testData(5);
        assertThat(15d).isEqualTo(invoiceInput.totalTaxValue());
    }
}