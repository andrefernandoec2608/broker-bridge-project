package com.gernad.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gernad.kafka.service.EventMainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LibraryEventsConsumer {

    @Autowired
    private EventMainService eventMainService;

    @KafkaListener(topics = {"quito-events"})
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        eventMainService.processMainEvent(consumerRecord);
        log.info("ConsumerRecord: {} ", consumerRecord);
    }
}