package com.gerand.kafka.controller.intg;

import com.gerand.kafka.domain.EventMain;
import com.gerand.kafka.test.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = {"quito-events"})
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"})
class EventMainControllerIntgEmbeddedTest {

    @Autowired
    TestRestTemplate restTemplate; // It's gonna have the base path configured with a random port where the application spin up.

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    ObjectMapper objectMapper;

    private Consumer<Integer, String> consumer;

    @BeforeEach
    void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps("group1", "true", embeddedKafkaBroker));
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        consumer = new DefaultKafkaConsumerFactory<>(configs, new IntegerDeserializer(), new StringDeserializer()).createConsumer();
        consumer.subscribe(Collections.singletonList("quito-events"));
        embeddedKafkaBroker.consumeFromAllEmbeddedTopics(consumer);
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

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
    void eventMainSyn() throws InterruptedException {
        //Instantiate a producer
        //given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", MediaType.APPLICATION_JSON.toString());
        var httpEntity = new HttpEntity<>(TestUtil.getGenericEventMain(), httpHeaders);
        //when
        var response = restTemplate.exchange("/events/main_syn", HttpMethod.POST, httpEntity, EventMain.class);
        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        //Instantiate a consumer
        //when
        ConsumerRecords<Integer, String> consumerRecords = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(7));
        //then
        assertEquals(2, consumerRecords.count());
        consumerRecords.forEach(record -> {
            var libraryEventActual = TestUtil.parseMainEventRecord(objectMapper, record.value());
            assertEquals(TestUtil.getGenericEventMain(), libraryEventActual);
        });
    }
}