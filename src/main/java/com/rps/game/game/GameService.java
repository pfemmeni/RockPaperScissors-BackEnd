package com.rps.game.game;

import com.rps.game.game.*;
import com.rps.game.game.GameRepository;
import com.rps.game.token.TokenNotFoundException;
import com.rps.game.tokenGame.TokenGameEntity;
import com.rps.game.tokenGame.TokenGameRepository;
import com.rps.game.token.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class GameService {

    GameRepository gameRepository;
    TokenRepository tokenRepository;
    TokenGameRepository tokenGameRepository;

    public GameService(GameRepository gameRepository, TokenRepository tokenRepository, TokenGameRepository tokenGameRepository) {
        this.gameRepository = gameRepository;
        this.tokenRepository = tokenRepository;
        this.tokenGameRepository = tokenGameRepository;
    }

    public GameStatus startGame(String tokenId) {
        GameEntity game = createGame();

        TokenGameEntity tokenGame = new TokenGameEntity(
                UUID.randomUUID().toString(),
                tokenRepository.getOne(tokenId),
                game,
                "Owner"
        );

        tokenGameRepository.save(tokenGame);
        game.addToken(tokenGame);
        tokenRepository.getOne(tokenId).addGame(tokenGame);

        GameStatus gameStatus = new GameStatus(
                game.getId(),
                tokenRepository.getOne(tokenId).getName(),
                null,
                Status.OPEN,
                null,
                null);
        return gameStatus;
    }

    private GameEntity createGame() {
        GameEntity game = new GameEntity(UUID.randomUUID().toString(),
                new ArrayList<>());
        gameRepository.save(game);
        return game;
    }

    public GameStatus joinExistingGame() {

        return null;
    }


    public Stream<GameEntity> all() {
        return gameRepository.findAll().stream();
    }

    public GameStatus joinGame(String gameId, String tokenId) throws TokenNotFoundException {
        TokenGameEntity tokenGameToJoin = tokenGameRepository
                .findAll()
                .stream()
                .filter(tokenGameEntity -> tokenGameEntity
                        .getGame()
                        .getId()
                        .equals(gameId) &&
                        !tokenGameEntity
                                .getToken()
                                .getId().equals(tokenId))
                .findAny()
                .orElseThrow(TokenNotFoundException::new);

        TokenGameEntity tokenGame = new TokenGameEntity(
                UUID.randomUUID().toString(),
                tokenRepository.getOne(tokenId),
                gameRepository.getOne(gameId),
                "Joiner"
        );
        tokenGameRepository.save(tokenGame);
        gameRepository.getOne(gameId).addToken(tokenGame);
        tokenRepository.getOne(tokenId).addGame(tokenGame);

        GameStatus gameStatus = new GameStatus(
                gameRepository.getOne(gameId).getId(),
                tokenRepository.getOne(tokenId).getName(),
                null,
                Status.ACTIVE,
                tokenGameToJoin.getToken().getName(),
                null);
        return gameStatus;
    }
}
