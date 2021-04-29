package com.rps.game.user;

public class CreateUser {
    String tokenId;

    public static UserEntity createUser(String tokenId) {
        return new UserEntity(tokenId);
    }

}