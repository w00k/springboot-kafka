package com.wook.kafka.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.event.ListenerContainerIdleEvent;
import org.springframework.stereotype.Service;

@Service
public class KafkaRetryListenerContainerImpl implements KafkaRetryListenerContainer {

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    private final Logger logger = LoggerFactory.getLogger(KafkaRetryListenerContainer.class);

    /*
     * metodo que inicia el ListenerContainer con id 'retry'
     * */
    public void doStartRetryListenerContainer() {
        logger.info("doStartRetryMessageController ::: Start");

        try {
            if (!registry.getListenerContainer("retry").isRunning()) {
                logger.info("doStartRetryListenerContainer - starting");
                registry.getListenerContainer("retry").start();
            }
            logger.info("doStartRetryListenerContainer - It is resumening");
            registry.getListenerContainer("retry").resume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * metodo que detiene el ListenerContainer con id 'retry'
     * */
    public void doStopRetryListenerContainer() {
        logger.info("doStopRetryMessageController ::: Start");

        try {
            if (registry.getListenerContainer("retry").isRunning()) {
                logger.info("doStopRetryMessageController - It is stoping");
                registry.getListenerContainer("retry").stop();
            }
            logger.info("doStopRetryListenerContainer - It is pausing");
            registry.getListenerContainer("retry").pause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * metodo para detener el ListenerContainer, cuando no se encuentren registros
     * */
    @EventListener
    public void idle(ListenerContainerIdleEvent event) {
        logger.info("idle - Stopping idle container");
        doStopRetryListenerContainer();
    }
}
