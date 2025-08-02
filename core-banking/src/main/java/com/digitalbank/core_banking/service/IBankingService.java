package com.digitalbank.core_banking.service;

import com.digitalbank.core_banking.model.*;
import com.digitalbank.core_banking.model.customer.Customer;

import java.util.List;

public interface IBankingService {

    Customer createCustomer(Customer customer);

    Account openAccount(Long customerId, Account account);

    Transaction transfer(Long fromAccountId, Long toAccountId, float amount, String description);

    List<Account> getAccountsForCustomer(Long customerId);

    List<Transaction> getTransactionsForAccount(Long accountId);

    Customer getCustomer(Long id);
}
