package com.digitalbank.core_banking.service;

import com.digitalbank.core_banking.model.*;
import com.digitalbank.core_banking.model.customer.Customer;
import com.digitalbank.core_banking.repository.AccountRepository;
import com.digitalbank.core_banking.repository.CustomerRepository;
import com.digitalbank.core_banking.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BankingService implements IBankingService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    private final TransactionProcessor transactionProcessor;

    @Autowired
    public BankingService(CustomerRepository customerRepository,
                              AccountRepository accountRepository,
                              TransactionRepository transactionRepository,
                              TransactionProcessor transactionProcessor) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionProcessor = transactionProcessor;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Account openAccount(Long customerId, Account account) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        account.setOwner(customer);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Transaction transfer(Long fromAccountId, Long toAccountId, float amount, String description) {
        Account fromAccount = accountRepository.findById(fromAccountId).orElseThrow(() -> new RuntimeException("Source Account not found"));
        Account toAccount = accountRepository.findById(toAccountId).orElseThrow(() -> new RuntimeException("Destination Account not found"));

        if(fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        fromAccount.withdraw(fromAccount.getBalance() - amount);
        toAccount.deposit(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setCurrency(fromAccount.getCurrency());
        transaction.setDateTime(LocalDateTime.now());
        transaction.setDescription(description);

        Transaction savedTransaction = transactionRepository.save(transaction);

        // Process transaction event asynchronously (Kafka)
        transactionProcessor.processTransaction(savedTransaction);

        return savedTransaction;
    }

    @Override
    public List<Account> getAccountsForCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        return customer.getAccounts();
    }

    @Override
    public List<Transaction> getTransactionsForAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        return transactionRepository.findAll().stream()
                .filter(tx -> tx.getFromAccount().equals(account) || tx.getToAccount().equals(account))
                .toList();
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
