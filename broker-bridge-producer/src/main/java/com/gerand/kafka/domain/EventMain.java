package com.gerand.kafka.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record EventMain(
        Integer eventId,
        EventType eventType,
        @NotNull
        @Valid
        MessageMain message
) {
}
