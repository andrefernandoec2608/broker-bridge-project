package com.gerand.kafka.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerand.kafka.domain.EventMain;
import com.gerand.kafka.domain.EventType;
import com.gerand.kafka.domain.MessageMain;

public class TestUtil {

    public static MessageMain getGenericMessageMain() {
        return new MessageMain(77001, "GerandTest", "Gerand Test Description");
    }

    public static MessageMain getGenericMessageInvalid() {
        return new MessageMain(null, "", "Gerand Test Description");
    }

    public static EventMain getGenericEventMain() {
        return new EventMain(null, EventType.NEW, getGenericMessageMain());
    }

    public static EventMain getGenericEventInvalid() {
        return new EventMain(null, EventType.NEW, getGenericMessageInvalid());
    }

    public static EventMain parseMainEventRecord(ObjectMapper objectMapper, String json) {
        try {
            return objectMapper.readValue(json, EventMain.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
