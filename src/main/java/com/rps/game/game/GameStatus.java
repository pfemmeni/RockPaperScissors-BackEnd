package com.rps.game.game;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

public class GameStatus {

    @Id
    String id;
    String name;
    Sign move;
    Status game;
    String opponentName;
    Sign opponentMove;

    @JsonCreator
    public GameStatus(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("move") Sign move,
            @JsonProperty("game") Status game,
            @JsonProperty("opponentName") String opponentName,
            @JsonProperty("opponentMove") Sign opponentMove) {
        this.id = id;
        this.name = name;
        this.move = move;
        this.game = game;
        this.opponentName = opponentName;
        this.opponentMove = opponentMove;
    }
}
