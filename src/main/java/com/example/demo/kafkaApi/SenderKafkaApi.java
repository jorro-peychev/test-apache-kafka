package com.example.demo.kafkaApi;

import com.example.demo.Command;
import java.util.Properties;
import java.util.UUID;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Service;

@Service
public class SenderKafkaApi implements Command {

  @Override
  public void execute() {

    String topicName = "test";
    System.out.println("Enter message(type exit to quit)");

    //Configure the SenderKafkaApi
    Properties configProperties = new Properties();
    configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
    configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

    org.apache.kafka.clients.producer.Producer producer = new KafkaProducer<String, String>(configProperties);

    ProducerRecord<String, String> rec = new ProducerRecord<String, String>(
        topicName,
        UUID.randomUUID().toString(),
        "Msg");

    producer.send(rec, new Callback() {
      @Override
      public void onCompletion(RecordMetadata metadata, Exception exception) {

        System.out.printf("Completed");
      }
    });

    producer.close();
  }

  @Override
  public String getTitle() {

    return "Send message kafka api.";
  }
}
