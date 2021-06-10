package com.gmail.pzalejko.invoicegenerator.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

/**
 * Encapsulates all information that must be included in the invoice. It also takes cares of all the calculations
 * like a total price, total tax values etc.
 */
public record InvoiceInput(
        long accountId,
        String invNumber,
        List<Item> items,
        String clientDetails,
        LocalDate dueDate,
        LocalDate saleDate,
        LocalDate creationDate) {

    public double totalPriceValue() {
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : items) {
            total = total.add(BigDecimal.valueOf(item.totalPriceValue()));
        }
        return total.doubleValue();
    }

    public double totalTaxValue() {
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : items) {
            total = total.add(BigDecimal.valueOf(item.totalTaxValue()));
        }
        return total.doubleValue();
    }

    public record Item(
            String name,
            long count,
            String unit,
            Price price) {


        public double totalPriceValue() {
            return BigDecimal.valueOf(count)
                    .multiply(BigDecimal.valueOf(price.pricePerOne))
                    .doubleValue();
        }

        public double totalTaxValue() {
            return BigDecimal.valueOf(totalPriceValue())
                    .multiply(BigDecimal.valueOf(price.taxPerOne))
                    .divide(BigDecimal.valueOf(100), RoundingMode.CEILING)
                    .doubleValue();
        }
    }

    public record Price(
            double pricePerOne,
            double taxPerOne,
            Currency currency) {
    }
}
