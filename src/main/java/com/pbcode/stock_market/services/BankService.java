package com.pbcode.stock_market.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BankService {

    private final Map<String, Integer> stocks = new HashMap<>();

    public Map<String, Integer> getStocks() {
        return stocks;
    }

    public void setStocks(Map<String, Integer> newStocks) {
        stocks.clear();
        stocks.putAll(newStocks);
    }

    public boolean hasStock(String name) {
        return stocks.containsKey(name);
    }

    public int getQuantity(String name) {
        return stocks.getOrDefault(name, 0);
    }
}
