package com.gerand.kafka.controller.unit;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.gerand.kafka.controller.EventMainControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerand.kafka.controller.EventMainController;
import com.gerand.kafka.domain.EventMain;
import com.gerand.kafka.producer.EventMainProducer;
import com.gerand.kafka.test.TestUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
class EventMainControllerUnitTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock // Crea un mock (objeto falso) de EventMainProducer
    private EventMainProducer eventMainProducer;

    @InjectMocks // Inyecta ese mock dentro de EventMainController
    private EventMainController eventMainController;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        //  Se configura MockMvc para simular llamadas HTTP al controller (sin necesidad de levantar un servidor)
        mockMvc = MockMvcBuilders
                .standaloneSetup(eventMainController)
                .setControllerAdvice(new EventMainControllerAdvice())
                .build();
    }

    @Test
    void eventMainControllerAsyn() throws Exception {
        // given
        String json = objectMapper.writeValueAsString(TestUtil.getGenericEventMain());
        // Llamar a sendEventMainAsyn con un EventMain y no hace nada, solo simula que fue llamado con éxito.
        doNothing().when(eventMainProducer).sendEventMainAsyn(isA(EventMain.class));

        // when + then
        // Simular un POST HTTP a la URL
        mockMvc.perform(post("/events/main_asyn")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void eventMainControllerAsynInvalid() throws Exception {
        // given
        String json = objectMapper.writeValueAsString(TestUtil.getGenericEventInvalid());
        //Comentar porque no se usa.
        //doNothing().when(eventMainProducer).sendEventMainAsyn(isA(EventMain.class));

        // when + then
        mockMvc.perform(post("/events/main_asyn")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("message.messageCode - must not be blank,message.messageId - must not be null"));
    }
}