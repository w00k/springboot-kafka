package com.wook.kafka.producer.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wook.kafka.producer.config.KafkaConfiguration;
import com.wook.kafka.producer.config.LoggerConfiguration;
import com.wook.kafka.producer.model.Message;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Async
public class KafkaSubscriptionServiceImpl implements KafkaSubscriptionService  {

    private LoggerConfiguration logger = LoggerConfiguration.getLogger("KafkaSubscriptionServiceImpl");

    @Autowired
    private KafkaConfiguration kafkaConfiguration;

    @Async
    public void suscribeProduceMessageToKafka(Message messageObj) {

        logger.info("suscribeProduceMessageToKafka start");

        String topicName = kafkaConfiguration.getTopic();
        Properties props = new Properties();
        Producer<String, String> producer = null;

        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = null;

        //propiedades para conectarse a kafka
        props.put("bootstrap.servers", kafkaConfiguration.getBootstrapServers());
        props.put("security.protocol", kafkaConfiguration.getSecurityProtocol());
        props.put("sasl.mechanism", kafkaConfiguration.getSaslMechanism());
        props.put("sasl.jaas.config", kafkaConfiguration.getJasConfig());
        props.put("key.serializer", kafkaConfiguration.getKeySerializer());
        props.put("value.serializer",kafkaConfiguration.getValueSerializer());
        props.put("group.id", kafkaConfiguration.getGroupIdConfig());
        props.put("linger.ms", 1); // set to 0 let Producer can send message immediately

        json = gson.toJson(messageObj);

        logger.info("suscribeProduceMessageToKafka sending - message='" + messageObj.getMessage() + "'");

        //genero el producer, se agergan datos y se cierra la "cola"
        producer = new KafkaProducer<>(props);
        producer.send(new ProducerRecord<>(topicName, json)); //topic, key, String con formato json
        producer.close();

        logger.info("suscribeProduceMessageToKafka end");
    }
}
