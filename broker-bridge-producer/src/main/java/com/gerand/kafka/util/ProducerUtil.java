package com.gerand.kafka.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerand.kafka.domain.EventMain;
import com.gerand.kafka.domain.EventType;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public final class ProducerUtil {

    private static List<Header> createHeaderRecord() {
        return List.of(new RecordHeader("event-source", "scanner".getBytes()));
    }

    public static ProducerRecord<Integer, String> createRecord(String topic, ObjectMapper objectMapper, EventMain eventSent) throws JsonProcessingException {
        List<Header> recordHeaders = createHeaderRecord();
        Integer key = eventSent.eventId();
        String value = objectMapper.writeValueAsString(eventSent);
        return new ProducerRecord<>(topic, null, key, value, recordHeaders);
    }

    public static ResponseEntity<String> validateUpdatingEventMain(EventMain libraryEvent) {
        if (libraryEvent.eventId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please pass the LibraryEventId");
        }

        if (!EventType.UPDATE.equals(libraryEvent.eventType()))  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only UPDATE event type is supported");
        }
        return null;
    }
}
