package com.pbcode.stock_market.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChaosController {

    @PostMapping("/chaos")
    public ResponseEntity<?> chaos() {
        System.exit(1);
        return ResponseEntity.ok().build();
    }
}