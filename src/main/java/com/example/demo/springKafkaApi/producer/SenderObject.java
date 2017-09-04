package com.example.demo.springKafkaApi.producer;


import com.example.demo.Command;
import com.example.demo.builder.KafkaSender;
import com.example.demo.builder.PublishFailedException;
import com.example.demo.domain.Foo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SenderObject implements Command {

  private static final Logger LOGGER = LoggerFactory.getLogger(SenderObject.class);

  private KafkaSender kafkaProducer;


  public SenderObject(KafkaSender kafkaProducer) {

    this.kafkaProducer = kafkaProducer;
  }

  @Override
  public void execute() {

    Foo foo = new Foo();
//    KafkaMessageContainer<Foo> messageContainer = new KafkaMessageContainer<Foo>(
//        foo,
//        foo.getId(),
//        foo.hashCode());

    try {
      kafkaProducer.send("test", "type", "messageKey", 1, foo);
    }
    catch (PublishFailedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getTitle() {

    return "Send message to topic \"test\" without key.";
  }
}
