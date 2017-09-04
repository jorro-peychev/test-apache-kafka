package com.example.demo.springKafkaApi.producer;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class Sender {

  private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);


  protected KafkaTemplate<String, String> kafkaTemplate;
  protected ObjectMapper objectMapper;
  protected String topic;

  public Sender(KafkaTemplate<String, String> kafkaTemplate,
                ObjectMapper objectMapper,
                @Value("${builder.topic.helloworld}") String topic) {

    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
    this.topic = topic;
  }

  protected ListenableFutureCallback<SendResult<String, String>> getCallback(String message) {

    return new ListenableFutureCallback<SendResult<String, String>>() {
      @Override
      public void onSuccess(SendResult<String, String> result) {

//        LOGGER.info("Sent message='{}' with offset={}", message, result.getRecordMetadata().offset());
        System.out.println("  ");
        System.out.println("Spring               Kafka  ");
        System.out.println("                    ,---------------------------------------------------,");
        System.out.println("Msg ----send OK---> | "+result.getRecordMetadata().offset()+"| "+ message+" |");
        System.out.println("                    `---------------------------------------------------'");
        System.out.println("  ");
      }

      @Override
      public void onFailure(Throwable ex) {

        LOGGER.error("Unable to execute message='{}'", message, ex);
      }
    };
  }
}
