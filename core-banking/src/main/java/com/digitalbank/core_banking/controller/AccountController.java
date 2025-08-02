package com.digitalbank.core_banking.controller;

import com.digitalbank.core_banking.model.Account;
import com.digitalbank.core_banking.service.BankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final BankingService bankingService;

    public AccountController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<Account> openAccount(@PathVariable Long customerId, @RequestBody Account account) {
        Account created = bankingService.openAccount(customerId, account);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Account>> getAccountsByCustomer(@PathVariable Long customerId) {
        List<Account> accounts = bankingService.getAccountsForCustomer(customerId);
        return ResponseEntity.ok(accounts);
    }
}
