package com.example.demo.builder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KafkaMessage<T> {

  @NotNull
  private final UUID id;

  @NotEmpty
  private final String key;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
  private ZonedDateTime time;

  @NotNull
  private final String type;

  @NotNull
  @Valid
  private final KafkaPayload<T> payload;


  public KafkaMessage(@JsonProperty("id") final UUID id, @JsonProperty("key") final String key,
                      @JsonProperty("time") final ZonedDateTime time, @JsonProperty("type") final String type,
                      @JsonProperty("payload") final KafkaPayload<T> payload) {

    this.id = id;
    this.key = key;
    this.time = time;
    this.type = type;
    this.payload = payload;
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

  public String getType() {

    return type;
  }

  public KafkaPayload<T> getPayload() {

    return payload;
  }

  @Override
  public String toString() {

    return "KafkaMessage{" +
        "id=" + id +
        ", key='" + key + '\'' +
        ", time=" + time +
        ", type='" + type + '\'' +
        ", payload=" + payload +
        '}';
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KafkaMessage<?> that = (KafkaMessage<?>) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(key, that.key) &&
        Objects.equals(time, that.time) &&
        Objects.equals(type, that.type) &&
        Objects.equals(payload, that.payload);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, key, time, type, payload);
  }
}
