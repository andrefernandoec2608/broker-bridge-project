package com.gerand.kafka.controller.intg;

import com.gerand.kafka.domain.EventMain;
import com.gerand.kafka.test.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventMainControllerIntgTest {

    @Autowired
    TestRestTemplate restTemplate; // It's gonna have the base path configured with a random port where the application spin up.

    @Test
    void eventMainAsyn() {
        //given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", MediaType.APPLICATION_JSON.toString());
        var httpEntity = new HttpEntity<>(TestUtil.getGenericEventMain(), httpHeaders);
        //when
        var response = restTemplate.exchange("/events/main_asyn", HttpMethod.POST, httpEntity, EventMain.class);
        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void eventMainSyn() {
        //given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", MediaType.APPLICATION_JSON.toString());
        var httpEntity = new HttpEntity<>(TestUtil.getGenericEventMain(), httpHeaders);
        //when
        var response = restTemplate.exchange("/events/main_syn", HttpMethod.POST, httpEntity, EventMain.class);
        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}