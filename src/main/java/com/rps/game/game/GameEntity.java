package com.rps.game.game;

import com.rps.game.user.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "games")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GameEntity {
    @Id String id;

    @OneToMany(mappedBy = "game")
    List<UserEntity> users;

    public void addUser(UserEntity userEntity) {
        users.add(userEntity);
    }
}