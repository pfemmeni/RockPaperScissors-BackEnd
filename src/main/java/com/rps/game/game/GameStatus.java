package com.rps.game.game;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

public class GameStatus {
    String id;
    String name;
    String move;
    String game;
    String opponentName;
    String opponentMove;

    @JsonCreator
    public GameStatus(
            @JsonProperty("id") String id,//ge
            @JsonProperty("name") String name,//tok
            @JsonProperty("move") String move,//ge
            @JsonProperty("game") String game,//ge
            @JsonProperty("opponentName") String opponentName,
            @JsonProperty("opponentMove") String opponentMove) {
        this.id = id;
        this.name = name;
        this.move = move;
        this.game = game;
        this.opponentName = opponentName;
        this.opponentMove = opponentMove;
    }
}
