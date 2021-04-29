package com.rps.game.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class User {
    String id;
    String name;

    @JsonCreator
    public User(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name) {

        this.id = id;
        this.name = name;
    }
}
