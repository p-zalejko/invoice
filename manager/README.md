# Invoice generation domain

## Shared Kernel
```
Unit(HOUR, QTY)

Currency
└- value

VATpercent
└- value

Price
├- value
├- Currency
└- VATpercent
```

## Invoice (root aggregate)
```
Invoice
├- Id
├- InvoiceNumber
├- InvoiceDate
├- DueDate
├- items (1:N)
|  ├- Item
|  └─  Quantity
└─ Company (From) (1:1)
└─ Company (Client) (1:1)
   
```

## Company
```
Company
├─ Id
├─ Name
├─ Address (1:1)
|   ├─ Street
|   ├─ Number
|   ├─ Zip
|   ├─ City
|   └─ Country 
├─ CompanyTaxId
└─ BankAccountNumber
```

## Item

```

Item
├─ Id
├─ Name
├─ Description
├─ Unit
└─ Price

```

# Invoice read model

```
Invoice
├- Id
├- InvoiceNumber
├- InvoiceDate
├- DueDate
├- PriceSummary
|   ├- Currency
|   └- VATDistributions (1:N)
|       ├- VATpercent
|       └- Total
|   ├- VATTotal
|   ├- Nett
|   └- Gross
├- items (1:N)
|  ├- ItemId
|  ├- Name
|  ├- Unit
|  ├- Quantity
|  ├- PricePerUnit
|  ├- TotalNettValue
|  └- TotalGrossValue
|
└─ Company (From) (1:1)
   ├─ Id
   ├─ Name
   ├─ CompanyTaxId
   └─ BankAccountNumber
└─ Company (Client) (1:1)
   ├─ Id
   ├─ Name
   └─ CompamyTaxID 
   
```