package com.rps.game.Game;

import com.rps.game.Token.TokenEntity;
import com.rps.game.TokenGame.TokenGameEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Optional;

@Entity(name = "game")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GameEntity {
    @Id
    String id;
    Sign move;
    Status game;
    Sign opponentMove;

    @OneToMany(mappedBy = "game")
    List<TokenGameEntity> tokens;

    public void addToken(TokenGameEntity tokenGameEntity) {
        tokens.add(tokenGameEntity);
    }

    public boolean isOwner(TokenEntity token) {
        TokenGameEntity tokenGameEntity1 = getTokens().stream()
                .filter(tokenGameEntity -> tokenGameEntity.getToken().equals(token))
                .findFirst()
                .orElseThrow();
        return tokenGameEntity1.getType().equals(TokenGameEntity.TYPE_OWNER);
    }

    public String getOpponentName() {
        if (getTokens().size() < 2) {
            return "";
        }
        TokenGameEntity tokenGameEntity1 = getTokens().stream()
                .filter(tokenGameEntity -> tokenGameEntity.getType().equals(TokenGameEntity.TYPE_JOINER))
                .findFirst()
                .orElseThrow();
        return tokenGameEntity1.getToken().getName();
    }

    public String getOwnerName() {
        TokenGameEntity tokenGameEntity1 = getTokens().stream()
                .filter(tokenGameEntity -> tokenGameEntity.getType().equals(TokenGameEntity.TYPE_OWNER))
                .findFirst()
                .orElseThrow();
        return tokenGameEntity1.getToken().getName();
    }

    public Optional<Sign> move() {
        return Optional.ofNullable(move);
    }

    public Optional<Sign> opponentMove() {
        return Optional.ofNullable(opponentMove);
    }
}
