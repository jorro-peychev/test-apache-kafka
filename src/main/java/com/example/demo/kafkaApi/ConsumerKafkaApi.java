package com.example.demo.kafkaApi;

import com.example.demo.Command;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class ConsumerKafkaApi implements Command {

  @Override
  public void execute() {

    //Kafka consumer configuration settings
    String topicName = "test";
    Properties props = new Properties();

    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "my_group_id");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "1000");
    props.put("session.timeout.ms", "30000");
    props.put("key.deserializer",
              "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer",
              "org.apache.kafka.common.serialization.StringDeserializer");

    KafkaConsumer<String, String> consumer = new KafkaConsumer
        <String, String>(props);

    //Kafka Consumer subscribes list of topics here.
    consumer.subscribe(Arrays.asList(topicName));

    //print the topic name
    System.out.println("Subscribed to topic " + topicName);
    int i = 0;

    //while (true) {

//    List<TopicPartition> topicPartitions = new ArrayList<>();
//    topicPartitions.add(new TopicPartition("test", 1));
//    consumer.beginningOffsets(topicPartitions);

      ConsumerRecords<String, String> records = consumer.poll(100);
      for (ConsumerRecord<String, String> record : records)

      // print the offset,key and value for the consumer records.
      {
        System.out.printf("===========>>>offset = %d, key = %s, value = %s\n",
                          record.offset(), record.key(), record.value());
      }
    //}
  }


  @Override
  public String getTitle() {

    return "Kafka api consumer.";
  }
}
