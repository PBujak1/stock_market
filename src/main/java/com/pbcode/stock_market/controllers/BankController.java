package com.pbcode.stock_market.controllers;

import com.pbcode.stock_market.services.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/stocks")
    public ResponseEntity<?> getStocks() {
        return ResponseEntity.ok(Map.of("stocks", bankService.getStocks()));
    }

    @PostMapping("/stocks")
    public ResponseEntity<?> setStocks(@RequestBody Map<String, Object> body) {

        List<Map<String, Object>> stocks = (List<Map<String, Object>>) body.get("stocks");

        Map<String, Integer> newStocks = new HashMap<>();

        for (Map<String, Object> s : stocks) {
            String name = (String) s.get("name");
            Integer quantity = (Integer) s.get("quantity");
            newStocks.put(name, quantity);
        }

        bankService.setStocks(newStocks);
        return ResponseEntity.ok().build();
    }
}