package com.example.demo.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {

  private static final Logger LOG = LoggerFactory.getLogger(KafkaSender.class);

  @Autowired
  KafkaOperations<String, String> kafkaTemplate;
  @Autowired
  ObjectMapper objectMapper;


  public <T> void send(final String topic, final String type, final String key, final Integer kafkaVersion, final T payload) throws PublishFailedException {

    LOG.debug("publishing message {}, {}, {}", topic, type, payload);

    final KafkaMessage<T> message = new KafkaMessage<>(UUID.randomUUID(), key, payload, kafkaVersion);
    String jsonMessage;
    try {
      jsonMessage = objectMapper.writeValueAsString(message);
    }
    catch (final JsonProcessingException e) {
      throw new PublishFailedException("Failed to send message on Kafka queue, mapping of KafkaMessage to JSON failed.", e);
    }
    kafkaTemplate.send(topic, message.getKey(), jsonMessage).addCallback(
        stringStringSendResult -> LOG.info("Successfully sent on topic '{}' message: {}", topic, jsonMessage),
        (Throwable throwable) -> {
          try {
            throw new PublishFailedException("Failed to send message on Kafka queue.", throwable);
          }
          catch (PublishFailedException e) {
            e.printStackTrace();
          }
        }
                                                                        );
  }
}
