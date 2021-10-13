package com.wook.kafka.producer.service;

import com.wook.kafka.producer.model.Message;

public interface KafkaSubscriptionService {

    public void suscribeProduceMessageToKafka(Message messageObj);
}
