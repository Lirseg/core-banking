package com.digitalbank.core_banking.controller;

import com.digitalbank.core_banking.model.Transaction;
import com.digitalbank.core_banking.service.BankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final BankingService bankingService;

    public TransactionController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestParam Long fromAccountId,
                                                @RequestParam Long toAccountId,
                                                @RequestParam float amount,
                                                @RequestParam(required = false) String description) {

        Transaction transaction = bankingService.transfer(fromAccountId, toAccountId, amount, description);
        return ResponseEntity.ok(transaction);
    }
}
