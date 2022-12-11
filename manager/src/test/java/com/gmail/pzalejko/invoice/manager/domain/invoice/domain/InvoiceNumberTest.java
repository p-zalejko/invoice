package com.gmail.pzalejko.invoice.manager.domain.invoice.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InvoiceNumberTest {

    @Test
    public void getNext() {
        // given
        var number = new InvoiceNumber(1, 1, 2022);

        //when
        InvoiceNumber next = number.getNext();

        //then
        assertThat(next.toString()).isEqualTo("2/1/2022");
    }
}
