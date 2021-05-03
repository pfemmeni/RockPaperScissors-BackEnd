package com.rps.game.token;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder(toBuilder = true)
@Value
public class Token {  String id;
    String name;

    @JsonCreator
    public Token(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name) {

        this.id = id;
        this.name = name;
    }

}