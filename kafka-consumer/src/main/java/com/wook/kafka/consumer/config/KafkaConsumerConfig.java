package com.wook.kafka.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConsumerConfig {

    @Value("${app.kafka.subscribe.user}")
    private String user;

    @Value("${app.kafka.subscribe.token}")
    private String token;

    @Value("${app.kafka.retry.user}")
    private String userRetry;

    @Value("${app.kafka.retry.token}")
    private String tokenRetry;

    @Value("${app.kafka.subscribe.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${app.kafka.subscribe.security-protocol}")
    private String securityProtocol;

    @Value("${app.kafka.subscribe.ssl-mechanism}")
    private String saslMechanism;

    @Value("${app.kafka.subscribe.key-deserializer}")
    private String keyDeserializer;

    @Value("${app.kafka.subscribe.value-deserializer}")
    private String valueDeserializer;

    @Value("${app.kafka.subscribe.jas-config}")
    private String jasConfig;

    @Value("${app.kafka.subscribe.jas-config}")
    private String jasConfigRetry;

    @Value("${app.kafka.subscribe.group-id-config}")
    private String groupIdConfig;

    /*
     * factory con la configuracion del mensaje como String
     * */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();

        String jasConfigMain = jasConfig + " username=\"" + user + "\" password=\"" + token + "\";";

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
        props.put(SaslConfigs.SASL_MECHANISM, saslMechanism);
        props.put(SaslConfigs.SASL_JAAS_CONFIG, jasConfigMain);
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
     * listener con dependencia de MapDeserializer para generar el objeto
     * */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}

