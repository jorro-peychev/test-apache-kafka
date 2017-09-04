package com.example.demo.builder;

import com.fasterxml.jackson.core.JsonProcessingException;


public class PublishFailedException extends RuntimeException {

  public PublishFailedException(String s, JsonProcessingException e) {

  }

  public PublishFailedException(String s, Throwable throwable) {


  }
}
