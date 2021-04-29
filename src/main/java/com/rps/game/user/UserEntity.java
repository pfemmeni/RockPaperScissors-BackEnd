package com.rps.game.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashMap;
import java.util.List;


@Entity(name = "user")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    String id;
    String name;

    public UserEntity(String id) {
        this.id = id;
        this.name = "";
    }

}




