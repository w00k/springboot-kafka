package com.wook.kafka.consumer.service;

import com.google.gson.Gson;
import com.wook.kafka.consumer.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaRetryConsumerService {

    private final Logger logger = LoggerFactory.getLogger(KafkaRetryConsumerService.class);

    /*
     * metodo que lee los mensajes como String, utiliza el topico y el groupId para
     * ser usado por la configuracion de KafkaConsumerRetryConfig.
     *
     */
    @KafkaListener(id = "retry", topics = "${app.kafka.retry.topic}", groupId = "${app.kafka.retry.group-id-config}", containerFactory = "retryKafkaListenerContainerFactory")
    public void retryConsume(String key, String message) {

        Message messageObj = null;
        try {
            messageObj = new Gson().fromJson(message, Message.class);
            logger.info("retryConsume - Mensaje ::: " + messageObj.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
