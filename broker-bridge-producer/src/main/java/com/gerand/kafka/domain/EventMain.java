package com.gerand.kafka.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EventMain(
        Integer eventId,
        EventType eventType,
        @NotNull
        @Valid
        List<MessageMain> messages
) {
}
