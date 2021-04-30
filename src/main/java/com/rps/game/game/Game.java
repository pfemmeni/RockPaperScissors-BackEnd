package com.rps.game.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Game {
    String id;

    @JsonCreator
    public Game(@JsonProperty("id") String id) {
        this.id = id;
    }
}
