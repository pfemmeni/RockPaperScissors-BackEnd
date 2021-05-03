package com.rps.game.token;

import com.rps.game.tokenGame.TokenGameEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity(name = "token")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TokenEntity {
    public static TokenEntity create() {
        return new TokenEntity(UUID.randomUUID().toString(), null, new ArrayList<>());
    }

    @Id String id;
    String name;
    @OneToMany(mappedBy = "token")
    List<TokenGameEntity> games;

    public void addGame(TokenGameEntity tokenGameEntity) {
        games.add(tokenGameEntity);
    }

}

