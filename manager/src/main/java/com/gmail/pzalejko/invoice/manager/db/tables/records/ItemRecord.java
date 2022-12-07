/*
 * This file is generated by jOOQ.
 */
package com.gmail.pzalejko.invoice.manager.db.tables.records;


import com.gmail.pzalejko.invoice.manager.db.enums.ItemUnit;
import com.gmail.pzalejko.invoice.manager.db.tables.Item;

import java.math.BigDecimal;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ItemRecord extends UpdatableRecordImpl<ItemRecord> implements Record7<Integer, String, String, ItemUnit, BigDecimal, Integer, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.item.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.item.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.item.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.item.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.item.description</code>.
     */
    public void setDescription(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.item.description</code>.
     */
    public String getDescription() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.item.unit</code>.
     */
    public void setUnit(ItemUnit value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.item.unit</code>.
     */
    public ItemUnit getUnit() {
        return (ItemUnit) get(3);
    }

    /**
     * Setter for <code>public.item.price_value</code>.
     */
    public void setPriceValue(BigDecimal value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.item.price_value</code>.
     */
    public BigDecimal getPriceValue() {
        return (BigDecimal) get(4);
    }

    /**
     * Setter for <code>public.item.price_vat</code>.
     */
    public void setPriceVat(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.item.price_vat</code>.
     */
    public Integer getPriceVat() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>public.item.price_currency</code>.
     */
    public void setPriceCurrency(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.item.price_currency</code>.
     */
    public String getPriceCurrency() {
        return (String) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<Integer, String, String, ItemUnit, BigDecimal, Integer, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Integer, String, String, ItemUnit, BigDecimal, Integer, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Item.ITEM.ID;
    }

    @Override
    public Field<String> field2() {
        return Item.ITEM.NAME;
    }

    @Override
    public Field<String> field3() {
        return Item.ITEM.DESCRIPTION;
    }

    @Override
    public Field<ItemUnit> field4() {
        return Item.ITEM.UNIT;
    }

    @Override
    public Field<BigDecimal> field5() {
        return Item.ITEM.PRICE_VALUE;
    }

    @Override
    public Field<Integer> field6() {
        return Item.ITEM.PRICE_VAT;
    }

    @Override
    public Field<String> field7() {
        return Item.ITEM.PRICE_CURRENCY;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public String component3() {
        return getDescription();
    }

    @Override
    public ItemUnit component4() {
        return getUnit();
    }

    @Override
    public BigDecimal component5() {
        return getPriceValue();
    }

    @Override
    public Integer component6() {
        return getPriceVat();
    }

    @Override
    public String component7() {
        return getPriceCurrency();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public String value3() {
        return getDescription();
    }

    @Override
    public ItemUnit value4() {
        return getUnit();
    }

    @Override
    public BigDecimal value5() {
        return getPriceValue();
    }

    @Override
    public Integer value6() {
        return getPriceVat();
    }

    @Override
    public String value7() {
        return getPriceCurrency();
    }

    @Override
    public ItemRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public ItemRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public ItemRecord value3(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public ItemRecord value4(ItemUnit value) {
        setUnit(value);
        return this;
    }

    @Override
    public ItemRecord value5(BigDecimal value) {
        setPriceValue(value);
        return this;
    }

    @Override
    public ItemRecord value6(Integer value) {
        setPriceVat(value);
        return this;
    }

    @Override
    public ItemRecord value7(String value) {
        setPriceCurrency(value);
        return this;
    }

    @Override
    public ItemRecord values(Integer value1, String value2, String value3, ItemUnit value4, BigDecimal value5, Integer value6, String value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ItemRecord
     */
    public ItemRecord() {
        super(Item.ITEM);
    }

    /**
     * Create a detached, initialised ItemRecord
     */
    public ItemRecord(Integer id, String name, String description, ItemUnit unit, BigDecimal priceValue, Integer priceVat, String priceCurrency) {
        super(Item.ITEM);

        setId(id);
        setName(name);
        setDescription(description);
        setUnit(unit);
        setPriceValue(priceValue);
        setPriceVat(priceVat);
        setPriceCurrency(priceCurrency);
    }
}