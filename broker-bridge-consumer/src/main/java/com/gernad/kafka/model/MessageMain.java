package com.gernad.kafka.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class MessageMain {

    @Id
    @GeneratedValue
    private Integer messageId;

    @NotBlank
    private String messageCode;

    @NotBlank
    private String messageDescription;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @ToString.Exclude
    private EventMain event;
}