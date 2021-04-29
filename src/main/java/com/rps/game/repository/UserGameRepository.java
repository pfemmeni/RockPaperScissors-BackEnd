package com.rps.game.repository;

import com.rps.game.game.UserGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGameRepository extends JpaRepository<UserGameEntity, String> {
}
