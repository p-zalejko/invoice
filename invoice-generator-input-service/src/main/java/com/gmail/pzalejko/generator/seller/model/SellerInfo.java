package com.gmail.pzalejko.generator.seller.model;

/**
 * Information about the company that creates the invoice. In general, the company is the "user" i.e. it uses the system
 * for cresting invoices.
 * <p>
 * NOTE: in fact, information about the company could be stored within the dynamoDB record that has all the information
 * about the invoice (it triggers this lambda!)... then we would not have load them here. But it has not been done
 * deliberately so that we can play a bit with lambdas...
 */
public record SellerInfo(long id, String name, String address, String bankAccountNumber, String taxNumber) {
}
