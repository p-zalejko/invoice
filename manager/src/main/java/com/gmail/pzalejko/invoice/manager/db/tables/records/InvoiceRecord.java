/*
 * This file is generated by jOOQ.
 */
package com.gmail.pzalejko.invoice.manager.db.tables.records;


import com.gmail.pzalejko.invoice.manager.db.tables.Invoice;

import java.time.LocalDate;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class InvoiceRecord extends UpdatableRecordImpl<InvoiceRecord> implements Record6<Integer, String, LocalDate, LocalDate, Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.invoice.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.invoice.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.invoice.number</code>.
     */
    public void setNumber(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.invoice.number</code>.
     */
    public String getNumber() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.invoice.invoice_date</code>.
     */
    public void setInvoiceDate(LocalDate value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.invoice.invoice_date</code>.
     */
    public LocalDate getInvoiceDate() {
        return (LocalDate) get(2);
    }

    /**
     * Setter for <code>public.invoice.due_date</code>.
     */
    public void setDueDate(LocalDate value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.invoice.due_date</code>.
     */
    public LocalDate getDueDate() {
        return (LocalDate) get(3);
    }

    /**
     * Setter for <code>public.invoice.company_from_id</code>.
     */
    public void setCompanyFromId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.invoice.company_from_id</code>.
     */
    public Integer getCompanyFromId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.invoice.company_billto_id</code>.
     */
    public void setCompanyBilltoId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.invoice.company_billto_id</code>.
     */
    public Integer getCompanyBilltoId() {
        return (Integer) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, String, LocalDate, LocalDate, Integer, Integer> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Integer, String, LocalDate, LocalDate, Integer, Integer> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Invoice.INVOICE.ID;
    }

    @Override
    public Field<String> field2() {
        return Invoice.INVOICE.NUMBER;
    }

    @Override
    public Field<LocalDate> field3() {
        return Invoice.INVOICE.INVOICE_DATE;
    }

    @Override
    public Field<LocalDate> field4() {
        return Invoice.INVOICE.DUE_DATE;
    }

    @Override
    public Field<Integer> field5() {
        return Invoice.INVOICE.COMPANY_FROM_ID;
    }

    @Override
    public Field<Integer> field6() {
        return Invoice.INVOICE.COMPANY_BILLTO_ID;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getNumber();
    }

    @Override
    public LocalDate component3() {
        return getInvoiceDate();
    }

    @Override
    public LocalDate component4() {
        return getDueDate();
    }

    @Override
    public Integer component5() {
        return getCompanyFromId();
    }

    @Override
    public Integer component6() {
        return getCompanyBilltoId();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getNumber();
    }

    @Override
    public LocalDate value3() {
        return getInvoiceDate();
    }

    @Override
    public LocalDate value4() {
        return getDueDate();
    }

    @Override
    public Integer value5() {
        return getCompanyFromId();
    }

    @Override
    public Integer value6() {
        return getCompanyBilltoId();
    }

    @Override
    public InvoiceRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public InvoiceRecord value2(String value) {
        setNumber(value);
        return this;
    }

    @Override
    public InvoiceRecord value3(LocalDate value) {
        setInvoiceDate(value);
        return this;
    }

    @Override
    public InvoiceRecord value4(LocalDate value) {
        setDueDate(value);
        return this;
    }

    @Override
    public InvoiceRecord value5(Integer value) {
        setCompanyFromId(value);
        return this;
    }

    @Override
    public InvoiceRecord value6(Integer value) {
        setCompanyBilltoId(value);
        return this;
    }

    @Override
    public InvoiceRecord values(Integer value1, String value2, LocalDate value3, LocalDate value4, Integer value5, Integer value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached InvoiceRecord
     */
    public InvoiceRecord() {
        super(Invoice.INVOICE);
    }

    /**
     * Create a detached, initialised InvoiceRecord
     */
    public InvoiceRecord(Integer id, String number, LocalDate invoiceDate, LocalDate dueDate, Integer companyFromId, Integer companyBilltoId) {
        super(Invoice.INVOICE);

        setId(id);
        setNumber(number);
        setInvoiceDate(invoiceDate);
        setDueDate(dueDate);
        setCompanyFromId(companyFromId);
        setCompanyBilltoId(companyBilltoId);
    }
}
