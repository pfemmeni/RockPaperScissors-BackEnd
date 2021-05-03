package com.rps.game.repository;

import com.rps.game.controller.GameUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameUserRepository extends JpaRepository<GameUserEntity, String> {
}
