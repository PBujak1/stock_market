package com.pbcode.stock_market.services;

import com.pbcode.stock_market.Wallet;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WalletService {

    private final Map<String, Wallet> wallets = new HashMap<>();

    public Wallet getOrCreate(String walletId) {
        return wallets.computeIfAbsent(walletId, id -> {
            Wallet w = new Wallet();
            w.id = id;
            return w;
        });
    }

    public Wallet get(String walletId) {
        return wallets.get(walletId);
    }
}