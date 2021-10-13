package com.wook.kafka.producer.service;

import com.wook.kafka.producer.model.Message;

public interface RetryKafkaSubscriptionService {

    public void suscribeProduceMessageToKafka(Message messageObj);
}
