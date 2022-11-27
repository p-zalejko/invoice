package com.gmail.pzalejko.invoice.manager.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestControllerSecurityTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void test() {
        var response = restTemplate.getForEntity("/hello", String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
