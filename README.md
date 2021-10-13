# App para testear las configuraciones de una partición de Kafka

Esta app se divide en 2 partes, **kafka-producer** produce un mensaje en Kafka y **kafka-consumer** rescata el mensaje. Considerar esta app para testear las configuraciones de kafka. 

## Producer

Antes que nada, es necesario ingresar en **kafka-producer** y modificar los valores de configuración en el **application.yml**, los cuales generalmente son:
- topic
- user
- token
- bootstrap-servers
- group-id-config

Extracto de la configuración.

```yaml
app:
  kafka:
    suscribe:
      topic: ${TOPIC:XXXX-XXXXXX-stream-XXXXX}
      user: ${USER:cloud/stream-XXXX-noprod/XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX}
      token: ${TOKEN:XXXXXXXXXXXXXXXXXX}
      bootstrap-servers: ${BOOTSTRAP_SERVERS:XXXXXXXXXXXXXX:9092}
      security-protocol: ${SECURITY_PROTOCOL:SASL_SSL}
      sasl-mechanism: ${SASL_MECHANISM:PLAIN}
      key-serializer: ${KEY_SERIALIZER:org.apache.kafka.common.serialization.StringSerializer}
      value-serializer: ${VALUE_SERIALIZER:org.apache.kafka.common.serialization.StringSerializer}
      jas-config: ${JAS_CONFIG:org.apache.kafka.common.security.plain.PlainLoginModule required}
      ssl-mechanism: ${SSL_MECHANISM:PLAIN}
      group-id-config: ${GROUP_ID_CONFIG:XXXXXXXXXXXXXXXXXXXXXXXX}
    retry:
      topic: ${TOPIC:XXXXX-XXXX-stream-retry-XXXXX}
      user: ${USER:cloud/stream-XXXX-noprod/XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX}
      token: ${TOKEN:XXXXXXXXXXXXXXXXXX}
      bootstrap-servers: ${BOOTSTRAP_SERVERS:XXXXXXXXXXXXXX:9092}
      security-protocol: ${SECURITY_PROTOCOL:SASL_SSL}
      sasl-mechanism: ${SASL_MECHANISM:PLAIN}
      key-serializer: ${KEY_SERIALIZER:org.apache.kafka.common.serialization.StringSerializer}
      value-serializer: ${VALUE_SERIALIZER:org.apache.kafka.common.serialization.StringSerializer}
      jas-config: ${JAS_CONFIG:org.apache.kafka.common.security.plain.PlainLoginModule required}
      ssl-mechanism: ${SSL_MECHANISM:PLAIN}
      group-id-config: ${RETRY_SGROUP_ID_CONFIG:XXXXXXXXXXXXXXXXXXXXXXXX}
```

**Nota**: En la configuración para producir el mensaje, se tiene que serializar el mensaje con tipo StringSerializer, pero también puede ser que el DTO posea un metodo para serializar.

### Consumir servicio en partición 

| Method | URI | Parametros | Observacion |
| ------------- | ------------- | ------------- | ------------- |
| POST | /api/v1/subscription | message | Mensaje que se enviará a Kafka |

Ejemplo 

Request:
```json
{
    "message": "Hello World"
}
```

Response:
```json
{
    "message": "Hello World"
}
```

### Consumir servicio de retry en partición 

| Method | URI | Parametros | Observacion |
| ------------- | ------------- | ------------- | ------------- |
| POST | /api/v1/retry-subscription | message | Mensaje que se enviará a Kafka Retry|

Ejemplo 

Request:
```json
{
    "message": "Hello World on retry task"
}
```

Response:
```json
{
    "message": "Hello World on retry task"
}
```

## Suscriptor

Antes que nada, es necesario ingresar en **kafka-consumer** y modificar los valores de configuración en el **application.yml**, los cuales generalmente son:
- topic
- user
- token
- bootstrap-servers
- group-id-config

Extracto de la configuración.

```yaml
app:
  kafka:
    subscribe:
      topic: ${TOPIC:XXXXX-XXXXX-stream-XXXXX}
      user: ${USER:cloud/stream-XXXXX-noprod/XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX}
      token: ${TOKEN:XXXXXXXXXXXXXXXXXX}
      bootstrap-servers: ${BOOTSTRAP_SERVERS:XXXXXXXXXXXXXX:9092}
      security-protocol: ${SECURITY_PROTOCOL:SASL_SSL}
      sasl-mechanism: ${SASL_MECHANISM:PLAIN}
      key-deserializer: ${KEY_SERIALIZER:org.apache.kafka.common.serialization.StringDeserializer}
      value-deserializer: ${VALUE_SERIALIZER:org.apache.kafka.common.serialization.StringDeserializer}
      jas-config: ${JAS_CONFIG:org.apache.kafka.common.security.plain.PlainLoginModule required}
      ssl-mechanism: ${SSL_MECHANISM:PLAIN}
      group-id-config: ${GROUP_ID_CONFIG:XXXXXXXXXXXXXXXXXXXXXXXX}
    retry:
      topic: ${RETRY_TOPIC:XXXX-XXXXX-stream-retry-XXXXX}
      user: ${RETRY_USER:tdecloud/stream-allot-noprod/XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX}
      token: ${RETRY_TOKEN:XXXXXXXXXXXXXXXXXX}
      bootstrap-servers: ${RETRY_BOOTSTRAP_SERVERS:XXXXXXXXXXXXXX:9092}
      security-protocol: ${RETRY_SECURITY_PROTOCOL:SASL_SSL}
      sasl-mechanism: ${RETRY_SASL_MECHANISM:PLAIN}
      key-deserializer: ${RETRY_KEY_SERIALIZER:org.apache.kafka.common.serialization.StringDeserializer}
      value-deserializer: ${RETRY_VALUE_SERIALIZER:org.apache.kafka.common.serialization.StringDeserializer}
      jas-config: ${RETRY_JAS_CONFIG:org.apache.kafka.common.security.plain.PlainLoginModule required}
      ssl-mechanism: ${RETRY_SSL_MECHANISM:PLAIN}
      group-id-config: ${RETRY_SGROUP_ID_CONFIG:XXXXXXXXXXXXXXXXXXXXXXXX}
```

**Nota**: En la configuración para consumer el mensaje, se tiene que deserializar el mensaje con tipo StringDeserializer, pero también puede ser que el DTO posea un metodo para deserializar.

### Consumir servicio para activar retry 

| Method | URI | Parametros | Observacion |
| ------------- | ------------- | ------------- | ------------- |
| GET | /v1/retry-message | Sin parámetros | Activa Listener de Kafka Retry |

Ejemplo 

Response:
```json
{
    "status" : "OK"
}
```