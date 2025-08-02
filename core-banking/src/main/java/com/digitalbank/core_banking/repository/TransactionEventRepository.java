package com.digitalbank.core_banking.repository;

import com.digitalbank.core_banking.model.TransactionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionEventRepository extends JpaRepository<TransactionEvent, Long> {
}

