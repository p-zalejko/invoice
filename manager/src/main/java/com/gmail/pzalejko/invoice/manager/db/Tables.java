/*
 * This file is generated by jOOQ.
 */
package com.gmail.pzalejko.invoice.manager.db;


import com.gmail.pzalejko.invoice.manager.db.tables.Company;
import com.gmail.pzalejko.invoice.manager.db.tables.CompanyAddress;
import com.gmail.pzalejko.invoice.manager.db.tables.FlywaySchemaHistory;
import com.gmail.pzalejko.invoice.manager.db.tables.Invoice;
import com.gmail.pzalejko.invoice.manager.db.tables.Invoiceitem;
import com.gmail.pzalejko.invoice.manager.db.tables.Item;


/**
 * Convenience access to all tables in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.company</code>.
     */
    public static final Company COMPANY = Company.COMPANY;

    /**
     * The table <code>public.company_address</code>.
     */
    public static final CompanyAddress COMPANY_ADDRESS = CompanyAddress.COMPANY_ADDRESS;

    /**
     * The table <code>public.flyway_schema_history</code>.
     */
    public static final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>public.invoice</code>.
     */
    public static final Invoice INVOICE = Invoice.INVOICE;

    /**
     * The table <code>public.invoiceitem</code>.
     */
    public static final Invoiceitem INVOICEITEM = Invoiceitem.INVOICEITEM;

    /**
     * The table <code>public.item</code>.
     */
    public static final Item ITEM = Item.ITEM;
}
