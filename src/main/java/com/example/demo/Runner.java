package com.example.demo;

import com.example.demo.kafkaApi.SenderKafkaApi;
import com.example.demo.kafkaApi.ConsumerKafkaApi;
import com.example.demo.springKafkaApi.producer.SendWithKeyOnDefaultTopic;
import com.example.demo.springKafkaApi.producer.SenderObject;
import com.example.demo.springKafkaApi.producer.SenderStringWithKey;
import com.example.demo.springKafkaApi.producer.SenderStringWithoutKey;
import com.example.demo.springKafkaApi.producer.SenderWithChannel;
import com.example.demo.springKafkaApi.producer.SenderWithMessageBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class Runner implements CommandLineRunner {

  private Map<Integer, Command> commandMap;

  public Runner(
      SenderStringWithoutKey sendStringWithoutKey,
      SendWithKeyOnDefaultTopic sendWithKeyOnDefaultTopic,
      SenderStringWithKey senderStringWithKey,
      SenderWithChannel senderWithChannel,
      SenderWithMessageBuilder senderWithMessageBuilder,
      SenderKafkaApi senderKafkaApi,
      ConsumerKafkaApi simpleConsumer,
      SenderObject senderObject
               ) {

    commandMap = new LinkedHashMap<>();
    commandMap.put(1, sendStringWithoutKey);
    commandMap.put(2, sendWithKeyOnDefaultTopic);
    commandMap.put(3, senderStringWithKey);
    commandMap.put(4, senderWithChannel);
    commandMap.put(5, senderWithMessageBuilder);
    commandMap.put(6, senderKafkaApi);
    commandMap.put(7, simpleConsumer);
    commandMap.put(8, senderObject);

    // TODO send with channel
    // TODO consume with channel
    // TODO not existing Topic
    // TODO send to topic with strict the messages
    // TODO send with partition
    // TODO batch listener --> spring.kafka.listener.type=batch
    //

    // TODO Consume messages configurable: newest, from beginning, from ...
    // TODO Readme docker builder, docker ui, builder ui
  }

  /**
   * Allow the user to send different kind of builder messages from command line.
   */
  @Override
  public void run(String... strings) throws Exception {

    printCommands();

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String line = "";

    while (line.equalsIgnoreCase("q") == false) {
      line = in.readLine();

      int commandNumber = parse(line);

      if (commandMap.containsKey(commandNumber)) {
        commandMap.get(commandNumber).execute();
      }
      else {
        System.out.println("No such command.");
        printCommands();
      }

    }
    in.close();
    System.exit(0);
  }

  private void printCommands() {

    System.out.println(",-------------------------------------------------------------");
    System.out.println("| Nb | Description ");
    System.out.println("|----|--------------------------------------------------------");
    commandMap.forEach((k, v) -> printCommand( k, v.getTitle()));
    System.out.println("| Type \"q\" to exit.");
    System.out.println("`------------------------------------------------------------");
  }

  private void printCommand(Integer number, String commandTitle){

    System.out.println(String.format("| %s  | %s.", number, commandTitle));
    System.out.println(String.format("|----|-------------------------------------------------------"));
  }

  private Integer parse(String line) {

    try {
      return Integer.valueOf(line);

    }
    catch (NumberFormatException e) {
      return 0;
    }
  }
}
