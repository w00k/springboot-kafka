package com.wook.kafka.producer.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wook.kafka.producer.config.KafkaRetryTopicConfiguration;
import com.wook.kafka.producer.config.LoggerConfiguration;
import com.wook.kafka.producer.model.Message;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@EnableAsync
public class RetryKafkaSubscriptionServiceImpl implements RetryKafkaSubscriptionService {

    private LoggerConfiguration logger = LoggerConfiguration.getLogger("RetryKafkaSubscriptionServiceImpl");

    @Autowired
    private KafkaRetryTopicConfiguration kafkaRetryTopicConfiguration;

    @Async
    public void suscribeProduceMessageToKafka(Message messageObj) {

        logger.info("produceMessageToKafka start");

        String retryTopic = kafkaRetryTopicConfiguration.getTopic();
        Properties properties = new Properties();
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = null;
        Producer<String, String> producer = null;

        properties.put("bootstrap.servers", kafkaRetryTopicConfiguration.getBootstrapServers());
        properties.put("security.protocol", kafkaRetryTopicConfiguration.getSecurityProtocol());
        properties.put("sasl.mechanism", kafkaRetryTopicConfiguration.getSaslMechanism());
        properties.put("sasl.jaas.config", kafkaRetryTopicConfiguration.getJasConfig());
        properties.put("key.serializer", kafkaRetryTopicConfiguration.getKeySerializer());
        properties.put("value.serializer", kafkaRetryTopicConfiguration.getValueSerializer());
        properties.put("group.id", kafkaRetryTopicConfiguration.getGroupIdConfig());

        producer = new KafkaProducer<String, String>(properties);
        json = gson.toJson(messageObj);

        logger.info("produceMessageToKafka sending - mesage = '" + messageObj.getMessage() + "'");
        producer.send(new ProducerRecord<String, String>(retryTopic, json));
        producer.close();

        logger.info("produceMessageToKafka end");
    }
}
