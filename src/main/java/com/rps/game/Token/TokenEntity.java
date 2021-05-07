package com.rps.game.Token;

import com.rps.game.TokenGame.TokenGameEntity;
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
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        return "TokenEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", games=" + games.stream().map(TokenGameEntity::getType).collect(Collectors.joining()) +
                '}';
    }
}

