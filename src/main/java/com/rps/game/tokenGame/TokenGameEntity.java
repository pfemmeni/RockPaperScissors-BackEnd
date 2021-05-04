package com.rps.game.tokenGame;

import com.rps.game.game.GameEntity;
import com.rps.game.token.TokenEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "token_game")
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenGameEntity {
    public static final String TYPE_OWNER = "Owner";
    public static final String TYPE_JOINER = "Joiner";
    @Id String id;

    @ManyToOne
    @JoinColumn(name = "token_id", nullable = false)
    TokenEntity token;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    GameEntity game;

    String type; //joiner eller owner

}
