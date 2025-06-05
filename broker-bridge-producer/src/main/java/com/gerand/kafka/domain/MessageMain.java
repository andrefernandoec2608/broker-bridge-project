package com.gerand.kafka.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MessageMain(
        @NotNull
        Integer messageId,
        @NotBlank
        String messageCode,
        @NotBlank
        String messageDescription
) {
}
