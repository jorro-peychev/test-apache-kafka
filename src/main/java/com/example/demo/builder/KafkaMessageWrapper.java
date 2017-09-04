package com.example.demo.builder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KafkaMessageWrapper<T> {

  @NotNull
  private final UUID id;

  @NotEmpty
  private final String key;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
  private ZonedDateTime time;

  @NotNull
  private final Integer version;

  @NotNull
  @Valid
  @JsonUnwrapped
  private final T message;


  public KafkaMessageWrapper(@JsonProperty("id") final UUID id, @JsonProperty("key") final String key,
                             @JsonProperty("payload") final T payload, Integer version) {

    this.id = id;
    this.key = key;
    this.version = version;
    this.time = ZonedDateTime.now(ZoneOffset.UTC);
    this.message = payload;
  }


  public UUID getId() {

    return id;
  }

  public String getKey() {

    return key;
  }

  public ZonedDateTime getTime() {

    return time;
  }

  public T getPayload() {

    return message;
  }

  @Override
  public String toString() {

    return "KafkaMessageWrapper{" +
        "id=" + id +
        ", key='" + key + '\'' +
        ", time=" + time +
        ", payload=" + message +
        '}';
  }
}
