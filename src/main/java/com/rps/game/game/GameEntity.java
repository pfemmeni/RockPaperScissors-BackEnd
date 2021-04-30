package com.rps.game.game;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "game")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GameEntity {
    @Id String id;

    @OneToMany(mappedBy = "game")
    List<GameUserEntity> users;

    public void addUser(GameUserEntity gameUserEntity) {
        users.add(gameUserEntity);
    }

    //bajs
}
