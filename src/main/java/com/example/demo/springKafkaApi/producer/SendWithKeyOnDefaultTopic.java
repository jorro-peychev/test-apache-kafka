package com.example.demo.springKafkaApi.producer;


import com.example.demo.Command;
import com.example.demo.springKafkaApi.producer.Sender;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendWithKeyOnDefaultTopic extends Sender implements Command {

  private static final Logger LOGGER = LoggerFactory.getLogger(SendWithKeyOnDefaultTopic.class);

  public SendWithKeyOnDefaultTopic(KafkaTemplate<String, String> kafkaTemplate,
                                   ObjectMapper objectMapper,
                                   @Value("${builder.topic.helloworld}") String topic) {

    super(kafkaTemplate, objectMapper, topic);
  }

  @Override
  public void execute() {

    String message = "Message " + UUID.randomUUID().toString() + ".";
    kafkaTemplate.setDefaultTopic(topic);
    LOGGER.info("sending payload='{}' to topic='{}'", message, topic);
    kafkaTemplate.sendDefault(UUID.randomUUID().toString(), message);
    kafkaTemplate.sendDefault(UUID.randomUUID().toString(), message);
    kafkaTemplate.sendDefault(UUID.randomUUID().toString(), message);
    kafkaTemplate.sendDefault(UUID.randomUUID().toString(), message);

    kafkaTemplate.flush();
  }

  @Override
  public String getTitle() {

    return "Send message to default topic \"test\" with Batch flush.";
  }
}
