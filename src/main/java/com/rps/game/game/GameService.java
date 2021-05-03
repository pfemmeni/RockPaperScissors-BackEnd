package com.rps.game.game;

import com.rps.game.game.*;
import com.rps.game.game.GameRepository;
import com.rps.game.token.TokenEntity;
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

    public GameEntity startGame(String tokenId) {
        GameEntity game = createGame(tokenId);

        TokenGameEntity tokenGame = new TokenGameEntity(
                UUID.randomUUID().toString(),
                tokenRepository.getOne(tokenId),
                game,
                "Owner"
        );

        tokenGameRepository.save(tokenGame);
        game.addToken(tokenGame);
        tokenRepository.getOne(tokenId).addGame(tokenGame);


        return game;
    }

    private GameEntity createGame(String tokenId) {
        GameEntity game = new GameEntity(UUID.randomUUID().toString(), null, Status.OPEN, null,
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

    public GameEntity joinGame(String gameId, String tokenId) throws TokenNotFoundException, TokenAllreadyJoinedToGameExeption {
        GameEntity gameToJoin = gameRepository.getOne(gameId);
        TokenEntity joinerToken = tokenRepository.getOne(tokenId);

        if (gameToJoin.getTokens().stream().anyMatch(tokenGameEntity -> tokenGameEntity.getToken().equals(joinerToken)))
            throw new TokenAllreadyJoinedToGameExeption();

        TokenGameEntity tokenGame = new TokenGameEntity(
                UUID.randomUUID().toString(),
                joinerToken,
                gameToJoin,
                "Joiner"
        );
        gameToJoin.addToken(tokenGame);
        joinerToken.addGame(tokenGame);
        tokenGameRepository.save(tokenGame);
        return gameToJoin;
    }
}
