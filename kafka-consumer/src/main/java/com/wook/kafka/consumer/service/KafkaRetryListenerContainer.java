package com.wook.kafka.consumer.service;

import org.springframework.kafka.event.ListenerContainerIdleEvent;

public interface KafkaRetryListenerContainer {

    public void doStartRetryListenerContainer();

    public void doStopRetryListenerContainer();

    public void idle(ListenerContainerIdleEvent event);
}
