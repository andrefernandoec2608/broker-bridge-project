package com.gerand.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("config")
public class AutoCreateConfig {
    @Value("${spring.kafka.newtopic}")
    public String newTopic;

    @Bean
    public NewTopic createNewTopic() {
        return TopicBuilder.name(newTopic)
                .partitions(3)
                .replicas(3)
                .build();
    }
}