package com.wook.kafka.consumer.controller;

import com.wook.kafka.consumer.service.KafkaRetryListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class KafkaRetryListenerContainerController {

    @Autowired
    private KafkaRetryListenerContainer kafkaRetryListenerContainer;

    /*
    * metodo que permite levantar la cola de retry
    * */
    @GetMapping("/retry-message")
    public ResponseEntity<String> doStartRetryMessageController() {

        kafkaRetryListenerContainer.doStartRetryListenerContainer();

        return new ResponseEntity<String>("{\"status\" : \"OK\"}", HttpStatus.OK);
    }
}
