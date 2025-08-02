package com.digitalbank.core_banking.service;

import com.digitalbank.core_banking.model.TransactionEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventConsumer {

    @KafkaListener(topics = "transactions", groupId = "core-banking-group")
    public void listen(ConsumerRecord<String, TransactionEvent> record) {
        String key = record.key();
        TransactionEvent event = record.value();

        System.out.println("Received message with key: " + key);
        System.out.println("Transaction Event: " + event);

        // אפשר להוסיף כאן עיבוד נוסף כנדרש
    }
}
