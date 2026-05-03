package com.pbcode.stock_market.controllers;

import com.pbcode.stock_market.Wallet;
import com.pbcode.stock_market.services.BankService;
import com.pbcode.stock_market.services.LogService;
import com.pbcode.stock_market.services.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class WalletController {

    private final BankService bankService;
    private final WalletService walletService;

    private final LogService logService;

    public WalletController(BankService bankService, WalletService walletService, LogService logService) {
        this.bankService = bankService;
        this.walletService = walletService;
        this.logService = logService;
    }

    @PostMapping("/wallets/{walletId}/stocks/{stockName}")
    public ResponseEntity<?> operate(
            @PathVariable String walletId,
            @PathVariable String stockName,
            @RequestBody Map<String, String> body
    ) {
        String type = body.get("type");

        // 404 – stock doesn't exist in bank
        if (!bankService.hasStock(stockName)) {
            return ResponseEntity.notFound().build();
        }

        Wallet wallet = walletService.getOrCreate(walletId);

        if ("buy".equals(type)) {
            // 400 – stock not avaliable
            if (bankService.getQuantity(stockName) <= 0) {
                return ResponseEntity.badRequest().build();
            }

            // bank -1
            bankService.getStocks().put(stockName, bankService.getQuantity(stockName) - 1);

            // wallet +1
            wallet.stocks.put(stockName, wallet.stocks.getOrDefault(stockName, 0) + 1);

            logService.add("buy", walletId, stockName);
            return ResponseEntity.ok().build();
        }

        if ("sell".equals(type)) {
            int walletQty = wallet.stocks.getOrDefault(stockName, 0);

            // 400 – stock not available in wallet
            if (walletQty <= 0) {
                return ResponseEntity.badRequest().build();
            }

            // wallet -1
            wallet.stocks.put(stockName, walletQty - 1);

            // bank +1
            bankService.getStocks().put(stockName, bankService.getQuantity(stockName) + 1);

            logService.add("sell", walletId, stockName);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<?> getWallet(@PathVariable String walletId) {
        Wallet wallet = walletService.get(walletId);

        if (wallet == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(walletToResponse(wallet));
    }

    private Map<String, Object> walletToResponse(Wallet wallet) {
        return Map.of(
                "id", wallet.id,
                "stocks", wallet.stocks.entrySet().stream()
                        .map(e -> Map.of(
                                "name", e.getKey(),
                                "quantity", e.getValue()
                        ))
                        .toList()
        );
    }

    @GetMapping("/wallets/{walletId}/stocks/{stockName}")
    public ResponseEntity<?> getWalletStock(@PathVariable String walletId, @PathVariable String stockName) {
        Wallet wallet = walletService.get(walletId);

        if (wallet == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                wallet.stocks.getOrDefault(stockName, 0)
        );
    }
}