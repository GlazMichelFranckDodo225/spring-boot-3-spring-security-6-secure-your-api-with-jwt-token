package com.dgmf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
    @PostMapping
    public ResponseEntity<String> home() {
        System.out.println("Stack Trace - HomeController - home()");

        return ResponseEntity.ok("Hello from Secured Endpoint");
    }
}
