package com.gerand.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerand.kafka.domain.EventMain;
import com.gerand.kafka.util.ProducerUtil;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventMainProducer {

    @Value("${spring.kafka.topic}")
    private String topic;
    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendEventMainAsyn(EventMain eventSent) throws JsonProcessingException {
        ProducerRecord<Integer, String> producerRecord = ProducerUtil.createRecord(topic, objectMapper, eventSent);
        kafkaTemplate.send(producerRecord)
                .thenAccept(result -> {
                    log.info(
                            "Message Sent Asyn Successfully for the key: {} and the partition is {}",
                            eventSent.eventId(), result.getRecordMetadata().partition());
                })
                .exceptionally(ex -> {
                    log.error("Error Sending the Message. Exception: {}", ex.getMessage(), ex);
                    return null;
                });
    }

    public void sendEventMainSyn(EventMain eventSent) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        ProducerRecord<Integer, String> producerRecord = ProducerUtil.createRecord(topic, objectMapper, eventSent);
        var sendResult = kafkaTemplate.send(producerRecord).get(3, TimeUnit.SECONDS);
        log.info("Message Sent Syn Successfully for the key: {} and the partition is {}",
                eventSent.eventId(), sendResult.getRecordMetadata().partition());
    }
}