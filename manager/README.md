# Domains and subdomains

## Shared Kernel
```
- Unit
- Currency
```

## Invoice
```
Invoice
├- Id
├- InvoiceNumber
├- InvoiceDate
├- DueDate
├- PriceSummary:
|   ├- Currency
|   └- VATDistributions (1:N)
|       ├- VATpercent
|       └- Total
|   ├- VATTotal
|   ├- Nett
|   └- Gross
├- InvoiceProducts (1:N)
|  ├- ProductId
|  ├- Name
|  ├- Unit
|  ├- Quantity
|  └- Price
|     ├- PricePerUnit
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
├─ Unit
├─ VATpercent
├─ Price
└─ Currency

```