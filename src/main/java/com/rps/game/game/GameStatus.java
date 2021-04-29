package com.rps.game.game;

import org.springframework.data.annotation.Id;

public class GameStatus {

    @Id String id;
    String name;

    Sign move;

    Status game;



    //User owner;

    //User joiner;
}
