package com.gernad.kafka.dto;

import java.util.List;

public record EventMainDTO(
        Integer eventId,
        EventType eventType,
        List<MessageMainDTO> messages
) {
}