CREATE TYPE public.item_Unit AS ENUM ('HOUR', 'QTY');

CREATE TABLE public.Item
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(25) NOT NULL,
    description    VARCHAR(50) NOT NULL,
    unit           item_Unit   NOT NULL,
    price_value    DECIMAL(6, 2),
    price_vat      int,
    price_currency VARCHAR(3)  NOT NULL

);

CREATE TABLE public.Company_Address
(
    id      SERIAL PRIMARY KEY,
    street  VARCHAR(25) NOT NULL,
    number  VARCHAR(10) NOT NULL,
    zip     VARCHAR(10) NOT NULL,
    city    VARCHAR(25) NOT NULL,
    country VARCHAR(25) NOT NULL
);

CREATE TABLE public.Company
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(25) NOT NULL,
    taxId          VARCHAR(10) NOT NULL,
    account_number VARCHAR(26) NOT NULL,
    address_id     int,

    UNIQUE (taxId),
    UNIQUE (account_number),
    FOREIGN KEY (address_id) REFERENCES public.Company_Address (id) MATCH SIMPLE
);

CREATE TABLE public.Invoice
(
    id                SERIAL PRIMARY KEY,
    number            VARCHAR(25) NOT NULL,
    invoice_date      date        NOT NULL,
    due_date          date        NOT NULL,
    company_from_id   int,
    company_billTo_id int,

    UNIQUE (number),
    FOREIGN KEY (company_from_id) REFERENCES public.Company (id) MATCH SIMPLE,
    FOREIGN KEY (company_billTo_id) REFERENCES public.Company (id) MATCH SIMPLE
);

CREATE TABLE public.InvoiceItem
(
    id             SERIAL PRIMARY KEY,
    item_id        int,
    invoice_id     int,
    name           VARCHAR(25) NOT NULL,
    unit           item_Unit   NOT NULL,
    quantity       int,
    price_value    DECIMAL(6, 2),
    price_vat      int,
    price_currency VARCHAR(3)  NOT NULL,

    FOREIGN KEY (item_id) REFERENCES public.Item (id) MATCH SIMPLE,
    FOREIGN KEY (invoice_id) REFERENCES public.Invoice (id) MATCH SIMPLE
);
