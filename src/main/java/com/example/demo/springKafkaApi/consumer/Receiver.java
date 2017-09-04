package com.example.demo.springKafkaApi.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Receiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

  @KafkaListener(topics = "${builder.topic.helloworld}")
  public void receive(String payload) {

//    LOGGER.info("received payload='{}'", payload);
    System.out.println("  ");
    System.out.println(" ,------,");
    System.out.println("| Kafka |----received OK---> [" + payload + "].");
    System.out.println(" `------'");
    System.out.println("  ");
  }

  @KafkaListener(topics = "tttt")
  public void receiveC(String payload) {

//    LOGGER.info("received payload='{}'", payload);
    // new topic listerner
    System.out.println("  ");
    System.out.println(" ,------,");
    System.out.println("| Kafka |----received OK---> [" + payload + "].");
    System.out.println(" `------'");
    System.out.println("  ");
  }
}
