package com.pbcode.stock_market.controllers;

import com.pbcode.stock_market.services.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/log")
    public ResponseEntity<?> getLog() {
        return ResponseEntity.ok(
                Map.of("log", logService.getAll())
        );
    }
}