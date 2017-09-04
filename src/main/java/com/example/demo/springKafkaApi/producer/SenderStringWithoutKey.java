package com.example.demo.springKafkaApi.producer;


import com.example.demo.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class SenderStringWithoutKey extends com.example.demo.springKafkaApi.producer.Sender implements Command {

  public SenderStringWithoutKey(KafkaTemplate<String, String> kafkaTemplate,
                                ObjectMapper objectMapper,
                                @Value("${builder.topic.helloworld}") String topic) {

    super(kafkaTemplate, objectMapper, topic);
  }

  @Override
  public void execute() {

    String message = "Message " + UUID.randomUUID().toString() + ".";
    ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
    future.addCallback(getCallback(message));
  }

  @Override
  public String getTitle() {

    return "Send message to topic \"test\" without key.";
  }
}
