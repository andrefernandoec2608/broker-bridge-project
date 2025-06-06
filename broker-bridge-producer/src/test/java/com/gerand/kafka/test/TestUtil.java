package com.gerand.kafka.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerand.kafka.domain.EventMain;
import com.gerand.kafka.domain.EventType;
import com.gerand.kafka.domain.MessageMain;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static List<MessageMain> getGenericMessageMain() {
        List<MessageMain> lista = new ArrayList<>();
        lista.add(new MessageMain(77001, "GerandTest", "Gerand Test Description"));
        return lista;
    }

    public static List<MessageMain> getGenericMessageInvalid() {
        List<MessageMain> lista = new ArrayList<>();
        lista.add(new MessageMain(null, "", "Gerand Test Description"));
        return lista;
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
