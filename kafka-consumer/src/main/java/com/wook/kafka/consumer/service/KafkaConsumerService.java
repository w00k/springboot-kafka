package com.wook.kafka.consumer.service;

import com.google.gson.Gson;
import com.wook.kafka.consumer.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    /*
     * metodo que lee los mensajes como Map, utiliza el topico y el groupId para ser usado por la configuracion de KafkaConsumerConfig
     * */
    @KafkaListener(topics = "${app.kafka.subscribe.topic}", groupId = "${app.kafka.subscribe.group-id-config}")
    public void mainConsume(String message) {

        Message messageObj = null;

        try {
            messageObj = new Gson().fromJson(message, Message.class);
            logger.info("Message ::: " + messageObj.getMessage());
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("Message empty");
        }
    }
}
