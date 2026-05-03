package com.pbcode.stock_market;

public class LogEntry {
    public String type;
    public String wallet_id;
    public String stock_name;

    public LogEntry(String type, String walletId, String stockName) {
        this.type = type;
        this.wallet_id = walletId;
        this.stock_name = stockName;
    }
}