package com.gernad.kafka.model;

import com.gernad.kafka.dto.EventType;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class EventMain {
    @Id
    @GeneratedValue
    private Integer eventId;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @Valid
    @ToString.Exclude
    private List<MessageMain> messages = new ArrayList<>();
}
