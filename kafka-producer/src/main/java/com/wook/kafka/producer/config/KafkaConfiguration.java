package com.wook.kafka.producer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableConfigurationProperties
public class KafkaConfiguration {

    @Value("${app.kafka.suscribe.topic}")
    private String topic;

    @Value("${app.kafka.suscribe.user}")
    private String user;

    @Value("${app.kafka.suscribe.token}")
    private String token;

    @Value("${app.kafka.suscribe.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${app.kafka.suscribe.security-protocol}")
    private String securityProtocol;

    @Value("${app.kafka.suscribe.ssl-mechanism}")
    private String saslMechanism;

    @Value("${app.kafka.suscribe.key-serializer}")
    private String keySerializer;

    @Value("${app.kafka.suscribe.value-serializer}")
    private String valueSerializer;

    @Value("${app.kafka.suscribe.jas-config}")
    private String jasConfig;

    @Value("${app.kafka.suscribe.group-id-config}")
    private String groupIdConfig;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
    }

    public String getSaslMechanism() {
        return saslMechanism;
    }

    public void setSaslMechanism(String saslMechanism) {
        this.saslMechanism = saslMechanism;
    }

    public String getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(String keySerializer) {
        this.keySerializer = keySerializer;
    }

    public String getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(String valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public String getJasConfig() {
        return jasConfig + " username=\"" + user + "\" password=\"" + token + "\";";
    }

    public void setJasConfig(String jasConfig) {
        this.jasConfig = jasConfig;
    }

    public String getGroupIdConfig() {
        return groupIdConfig;
    }

    public void setGroupIdConfig(String groupIdConfig) {
        this.groupIdConfig = groupIdConfig;
    }

    public KafkaConfiguration() {
        super();
    }
}
