package com.philips.productcatalog.core.autoconfigure.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for kafka topic.
 */
@Configuration
public class KafkaTopicConfiguration {

    @Value(value = "${spring.kafka.bootstrapAddress}")
    private String bootstrapAddress;

    /**
     * Kafka admin.
     *
     * @return {@link KafkaAdmin}
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        final Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }
}
