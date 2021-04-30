package com.rps.game.game;

import org.springframework.data.annotation.Id;

public class GameStatus {

    @Id String id; //game idt

    String name; //kan vara null

    Sign move; //sten sax påse

    Status game; //win or lose

    //denna



    //User owner;

    //User joiner;
}
