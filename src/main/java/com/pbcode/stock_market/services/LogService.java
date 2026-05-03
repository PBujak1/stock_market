package com.pbcode.stock_market.services;

import com.pbcode.stock_market.LogEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {

    private final List<LogEntry> log = new ArrayList<>();

    public void add(String type, String walletId, String stockName) {
        log.add(new LogEntry(type, walletId, stockName));
    }

    public List<LogEntry> getAll() {
        return log;
    }
}
