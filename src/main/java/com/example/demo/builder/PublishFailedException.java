package com.example.demo.builder;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by georgi.peychev on 9/4/17.
 */
public class PublishFailedException extends Throwable {

  public PublishFailedException(String s, JsonProcessingException e) {

  }

  public PublishFailedException(String s, Throwable throwable) {


  }
}
