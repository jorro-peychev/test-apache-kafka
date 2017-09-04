package com.example.demo.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KafkaPayload<T> {

    @NotNull
    private final Integer version;

    @NotNull
    @Valid
    @JsonUnwrapped
    private final T message;

    public KafkaPayload(@JsonProperty("version") final Integer version, @JsonProperty("message") final T message) {
        this.version = version;
        this.message = message;
    }

    public Integer getVersion() {
        return version;
    }


    public T getMessage() {
        return message;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        KafkaPayload rhs = (KafkaPayload) obj;
        return this.version.equals( rhs.version) && this.message.equals( rhs.message);
    }

    @Override
    public int hashCode() {

        return Objects.hash(version, message);
    }

    @Override
    public String toString() {

        return "KafkaPayload{" +
            "version=" + version +
            ", message=" + message +
            '}';
    }
}
