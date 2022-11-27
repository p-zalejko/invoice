package com.gmail.pzalejko.invoice.manager.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ExampleController {

    @GetMapping("/hello")
    String get(){
        return "hello";
    }
}
