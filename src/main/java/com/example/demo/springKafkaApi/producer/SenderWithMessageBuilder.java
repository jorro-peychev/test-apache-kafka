package com.example.demo.springKafkaApi.producer;


import com.example.demo.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class SenderWithMessageBuilder extends com.example.demo.springKafkaApi.producer.Sender implements Command {

  private static final Logger LOGGER = LoggerFactory.getLogger(SenderWithMessageBuilder.class);

  public SenderWithMessageBuilder(KafkaTemplate<String, String> kafkaTemplate,
                                  ObjectMapper objectMapper,
                                  @Value("${builder.topic.helloworld}") String topic) {

    super(kafkaTemplate, objectMapper, topic);
  }

  @Override
  public void execute() {

    String message = "Message " + UUID.randomUUID().toString() + ".";
    ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(MessageBuilder.withPayload(message)
                                                                                           .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                                                                                           .setHeader(KafkaHeaders.TOPIC, topic)
                                                                                           .build()
                                                                            );
    future.addCallback(getCallback(message));
  }

  @Override
  public String getTitle() {

    return "Send message to topic \"test\" with message builder.";
  }
}
