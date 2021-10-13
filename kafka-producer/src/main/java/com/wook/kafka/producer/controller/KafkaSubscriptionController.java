package com.wook.kafka.producer.controller;

import com.wook.kafka.producer.config.LoggerConfiguration;
import com.wook.kafka.producer.model.Message;
import com.wook.kafka.producer.service.KafkaSubscriptionService;
import com.wook.kafka.producer.service.RetryKafkaSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class KafkaSubscriptionController {

    @Autowired
    private KafkaSubscriptionService kafkaSubscriptionService;

    @Autowired
    private RetryKafkaSubscriptionService retryKafkaSubscriptionService;

    private LoggerConfiguration logger = LoggerConfiguration.getLogger("KafkaSubscriptionController");

    @PostMapping("/api/v1/subscription")
    public ResponseEntity<Message> serviceSubscription(@RequestBody Message messageRequest) {

        logger.setTransId(UUID.randomUUID().toString());
        logger.info("serviceSubscription start");
        kafkaSubscriptionService.suscribeProduceMessageToKafka(messageRequest);

        return new ResponseEntity<Message>(messageRequest, HttpStatus.OK);
    }

    @PostMapping("/api/v1/retry-subscription")
    public ResponseEntity<Message> serviceRetrySubscription(@RequestBody Message messageRequest) {

        logger.setTransId(UUID.randomUUID().toString());
        logger.info("serviceRetrySubscription start");
        retryKafkaSubscriptionService.suscribeProduceMessageToKafka(messageRequest);

        return new ResponseEntity<Message>(messageRequest, HttpStatus.OK);
    }
}
