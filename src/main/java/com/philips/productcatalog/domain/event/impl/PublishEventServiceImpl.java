package com.philips.productcatalog.domain.event.impl;

import com.philips.productcatalog.core.autoconfigure.kafka.KafkaConfigurationProperties;
import com.philips.productcatalog.domain.event.PublishEventService;
import com.philips.productcatalog.domain.event.SyncErrorEvent;
import com.philips.productcatalog.infrastructure.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublishEventServiceImpl implements PublishEventService {

    private final KafkaConfigurationProperties kafkaConfigurationProperties;
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public void publishErrorSyncEvent(String httpMethod, Product product) {
        var syncErrorEvent = new SyncErrorEvent(httpMethod, product);
        log.info("Posting sync error event {}", syncErrorEvent);
        kafkaTemplate.send(kafkaConfigurationProperties.getTopic(), syncErrorEvent.toString());
    }
}
