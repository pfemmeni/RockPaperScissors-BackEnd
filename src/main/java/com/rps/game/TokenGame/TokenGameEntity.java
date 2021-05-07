package com.rps.game.TokenGame;

import com.rps.game.Game.GameEntity;
import com.rps.game.Token.TokenEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "token_game")
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenGameEntity {
    public static final String TYPE_OWNER = "Owner";
    public static final String TYPE_JOINER = "Joiner";
    @Id String id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "token_id", nullable = false)
    TokenEntity token;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id", nullable = false)
    GameEntity game;

    String type; //joiner eller owner

}
