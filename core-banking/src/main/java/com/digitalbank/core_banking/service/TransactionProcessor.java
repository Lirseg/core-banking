package com.digitalbank.core_banking.service;

import com.digitalbank.core_banking.model.Transaction;
import com.digitalbank.core_banking.model.TransactionEvent;
import com.digitalbank.core_banking.model.Metadata;
import com.digitalbank.core_banking.repository.TransactionEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionProcessor {

    private final KafkaProducerService kafkaProducerService;
    private final TransactionEventRepository transactionEventRepository;

    @Autowired
    public TransactionProcessor(KafkaProducerService kafkaProducerService,
                                TransactionEventRepository transactionEventRepository) {
        this.kafkaProducerService = kafkaProducerService;
        this.transactionEventRepository = transactionEventRepository;
    }

    public void processTransaction(Transaction transaction) {
        // Create TransactionEvent
        TransactionEvent event = new TransactionEvent();
        event.setTransaction(transaction);
        event.setEventType("transaction");
        event.setTimestamp(LocalDateTime.now());

        Metadata metadata = new Metadata("bank-core-service", "api/v1/transfer");
        event.setMetadata(metadata);

        // Save event in DB
        transactionEventRepository.save(event);

        // Send event to Kafka
        kafkaProducerService.send(event);
    }
}
