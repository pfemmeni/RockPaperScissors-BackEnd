package com.rps.game.service;

import com.rps.game.game.GameEntity;
import com.rps.game.game.GameStatus;
import com.rps.game.game.GameUserEntity;
import com.rps.game.repository.GameRepository;
import com.rps.game.repository.UserGameRepository;
import com.rps.game.repository.UserRepository;
import com.rps.game.user.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class GameService {

    GameRepository gameRepository;
    UserRepository userRepository;
    UserGameRepository userGameRepository;

    public GameService(GameRepository gameRepository, UserRepository userRepository, UserGameRepository userGameRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.userGameRepository = userGameRepository;
    }

/*    public GameStatus createNewGame(String tokenId) {
        UserEntity user = userRepository.getOne(tokenId);
        GameEntity game = new GameEntity(UUID.randomUUID().toString(),
                new ArrayList<>());
        GameUserEntity userGame = new GameUserEntity(
                UUID.randomUUID().toString(),
                user,
                game,
                "Owner"
        );
        userGameRepository.save(userGame);
        gameRepository.save(game);
        game.addUser(user);
        return null;
    }

   public GameStatus joinExistingGame() {

    }*/


    public Stream<GameEntity> all() {
        return gameRepository.findAll().stream();
    }
}
