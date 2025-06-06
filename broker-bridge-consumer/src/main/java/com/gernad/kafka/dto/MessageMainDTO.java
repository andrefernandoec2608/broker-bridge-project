package com.gernad.kafka.dto;

public record MessageMainDTO(
        Integer messageId,
        String messageCode,
        String messageDescription
) {
}
