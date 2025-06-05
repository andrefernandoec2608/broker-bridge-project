package com.gerand.kafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gerand.kafka.domain.EventMain;
import com.gerand.kafka.producer.EventMainProducer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import com.gerand.kafka.util.ProducerUtil;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EventMainController {

    private final EventMainProducer eventMainProducer;

    @PostMapping("/events/main_asyn")
    public ResponseEntity<EventMain> eventMainAsyn(
            @RequestBody @Valid EventMain eventMain
    ) throws JsonProcessingException {
        eventMainProducer.sendEventMainAsyn(eventMain);
        log.info("Method eventMainAsyn finished.");
        return ResponseEntity.status(HttpStatus.CREATED).body(eventMain);
    }

    @PostMapping("/events/main_syn")
    public ResponseEntity<EventMain> eventMainSyn(
            @RequestBody EventMain eventMain
    ) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        eventMainProducer.sendEventMainSyn(eventMain);
        log.info("Method eventMainSyn finished.");
        return ResponseEntity.status(HttpStatus.CREATED).body(eventMain);
    }

    @PutMapping("/events/main_syn")
    public ResponseEntity<String> eventMainSynUpdate(
            @RequestBody EventMain eventMain
    ) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        ResponseEntity<String> BAD_REQUEST = ProducerUtil.validateUpdatingEventMain(eventMain);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        eventMainProducer.sendEventMainSyn(eventMain);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UPDATE event executed.");
    }
}
