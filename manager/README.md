# Domains and subdomains

## Invoice
```
Invoice
├- Id
├- InvoiceNumber
├- InvoiceDate
├- DueDate
├- Currency
├- VATPDistributions (1:N)
|  ├- VATpercent
|  └- Total
├- VATTotal
├- Nett
├- Gross
├- GrossAsText
├- InvoiceLProducts (1:N)
|  ├- ProductId
|  ├- ItemName
|  ├- SKU
|  ├- Quantity
|  └- Price
|     ├- PricePerSKU
|     ├- VATpercent
|     ├- TotalNettValue
|     └- TotalGrossValue
└─ Company (From) (1:1)
   ├─ Id
   ├─ Name
   ├─ CompamyTaxID
   └─ BankAccountNumber
└─ Company (Client) (1:1)
   ├─ Id
   ├─ Name
   └─ CompamyTaxID 
   
```

## Company
```
Company
├─ Id
├─ Name
├─ Street
├─ Zip
├─ City
├─ Country
├─ CompamyTaxID
└─ BankAccountNumber
```

## Product

```

Product
├─ Id
├─ Name
├─ Description
├─ SKU
├─ VATpercent
└─ PricePerSKU

```