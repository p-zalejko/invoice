package com.gmail.pzalejko.invoicegenerator.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InvoiceRequestOutput {

    private String status;
    private String fileUri;
}
