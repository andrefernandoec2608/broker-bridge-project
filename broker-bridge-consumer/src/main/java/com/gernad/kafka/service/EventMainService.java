package com.gernad.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gernad.kafka.dto.EventMainDTO;
import com.gernad.kafka.jpa.EventMainRepository;
import com.gernad.kafka.model.EventMain;
import com.gernad.kafka.model.MessageMain;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EventMainService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private EventMainRepository eventMainRepository;

    public void processMainEvent(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        EventMainDTO eventMainDTO = objectMapper.readValue(consumerRecord.value(), EventMainDTO.class);

        EventMain eventMain = EventMain.builder()
                .eventType(eventMainDTO.eventType())
                .build();

        List<MessageMain> messageEntities = eventMainDTO.messages().stream()
                .map(dto -> MessageMain.builder()
                        .messageCode(dto.messageCode())
                        .messageDescription(dto.messageDescription())
                        .event(eventMain)
                        .build())
                .toList();
        eventMain.setMessages(messageEntities);

        switch (eventMain.getEventType()) {
            case NEW:
                saveMainEvent(eventMain);
                break;
            case UPDATE:
                break;
            default:
                log.info("Invalid Library Event Type");
        }
    }

    private void saveMainEvent(EventMain eventMain) {
        eventMainRepository.save(eventMain);
        log.info("Successfully Persisted the libary Event {} ", eventMain);
    }
}
