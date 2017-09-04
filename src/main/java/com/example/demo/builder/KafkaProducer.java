package com.example.demo.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

  private static final int INITIAL_VERSION = 1;
  private static final Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);

  @Autowired
  KafkaOperations<String, String> kafkaTemplate;
  @Autowired
  ObjectMapper objectMapper;
//  private volatile boolean isShuttingDown = false;
//
//
//  @PreDestroy
//  public void shutDown() {
//
//    LOG.info("ShutDown was called .... {}", this.hashCode());
//    isShuttingDown = true;
//  }


//  public <T> void publish(final String topic, final String type, String messageKey, Integer kafkaVersion, final KafkaMessageContainer<T> messageContainer)
//      throws PublishFailedException {
//
//    final String functionalKey = messageContainer.getFunctionalKey();
//    send(topic, type, messageKey, kafkaVersion, messageContainer.getMessage());
//  }


  public <T> void send(final String topic, final String type, final String key, final Integer kafkaVersion, final T payload) throws PublishFailedException {

    LOG.debug("publishing message {}, {}, {}", topic, type, payload);

//    if (isShuttingDown) {
//      LOG.warn("Send skipped, due to application shutting down. {}", this.hashCode());
//      return;
//    }

    final KafkaPayload<T> kafkaPayload = new KafkaPayload<>(kafkaVersion, payload);
    final KafkaMessage<T> message = new KafkaMessage<>(UUID.randomUUID(), key,
                                                       ZonedDateTime.now(ZoneOffset.UTC), type, kafkaPayload);
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
