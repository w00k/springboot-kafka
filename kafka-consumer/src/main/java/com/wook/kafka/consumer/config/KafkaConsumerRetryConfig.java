package com.wook.kafka.consumer.config;

import com.wook.kafka.consumer.service.KafkaConsumerService;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerRetryConfig {

    /*
    * Configuracion de la cola de retry
    * */

    @Value("${app.kafka.retry.topic}")
    private String topic;

    @Value("${app.kafka.retry.user}")
    private String userRetry;

    @Value("${app.kafka.retry.token}")
    private String tokenRetry;

    @Value("${app.kafka.retry.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${app.kafka.retry.security-protocol}")
    private String securityProtocol;

    @Value("${app.kafka.retry.ssl-mechanism}")
    private String saslMechanism;

    @Value("${app.kafka.retry.key-deserializer}")
    private String keyDeserializer;

    @Value("${app.kafka.retry.value-deserializer}")
    private String valueDeserializer;

    @Value("${app.kafka.retry.jas-config}")
    private String jasConfig;

    @Value("${app.kafka.retry.group-id-config}")
    private String groupIdConfig;

    @Autowired
    KafkaConsumerService kafkaConsumerService;

    /*
    * despues de 30000 ms en que no existan mensajes se baja el Factory
    * */
    @Bean
    public ConsumerFactory<String, String> retryConsumerFactory() {

        Map<String, Object> props = new HashMap<>();

        String jasConfigRetry = jasConfig + " username=\"" + userRetry + "\" password=\""+ tokenRetry +"\";";

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
        props.put(SaslConfigs.SASL_MECHANISM, saslMechanism);
        props.put(SaslConfigs.SASL_JAAS_CONFIG, jasConfigRetry);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdConfig);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(props);
    }

    /*
    * el listener container factorty, inicia de forma manual
    * */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> retryKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(retryConsumerFactory());
        factory.setConcurrency(1);
        factory.getContainerProperties().setPollTimeout(100);
        factory.getContainerProperties().setIdleEventInterval(300000L);
        factory.setAutoStartup(Boolean.FALSE);
        return factory;
    }

}

