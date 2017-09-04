package com.example.demo.springKafkaApi.producer;


import com.example.demo.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SenderWithChannel extends com.example.demo.springKafkaApi.producer.Sender implements Command {

  private static final Logger LOGGER = LoggerFactory.getLogger(SenderWithChannel.class);

  public SenderWithChannel(KafkaTemplate<String, String> kafkaTemplate,
                           ObjectMapper objectMapper,
                           @Value("${builder.topic.helloworld}") String topic) {

    super(kafkaTemplate, objectMapper, topic);
  }

  @Override
  public void execute() {

    String message = "Message " + UUID.randomUUID().toString() + ".";
    //    Boolean
//        result = channel.execute(MessageBuilder.withPayload(message)
//                                            .setHeader(KafkaHeaders.MESSAGE_KEY, "key")
//                                            .setHeader(KafkaHeaders.TOPIC, topic)
//                                            .build()
//                             );
//    LOGGER.info("Status to execute message='{}'", result);

//    future.addCallback(getCallback(message));
  }

  @Override
  public String getTitle() {

    return "Send message to topic \"test\" throw channel.";
  }
}
