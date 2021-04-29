package com.rps.game.token;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;


@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Token {
    public static Token create() {
        return new Token(UUID.randomUUID().toString());
    }

    String id;
}