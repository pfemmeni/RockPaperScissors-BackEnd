package com.rps.game.game;

import com.rps.game.token.TokenEntity;
import com.rps.game.tokenGame.TokenGameEntity;
import com.rps.game.tokenGame.TokenGameRepository;
import com.rps.game.token.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        GameEntity game = createGame();
        TokenEntity token = tokenRepository.getOne(tokenId);

        TokenGameEntity tokenGame = createNewTokenGameEntity(game, token, TokenGameEntity.TYPE_OWNER);

        game.setGame(Status.OPEN);
        game.addToken(tokenGame);
        token.addGame(tokenGame);
        tokenGameRepository.save(tokenGame);
        return game;
    }

    private GameEntity createGame() {
        GameEntity game = new GameEntity(UUID.randomUUID().toString(), null, Status.OPEN, null,
                new ArrayList<>());
        gameRepository.save(game);
        return game;
    }

    public GameEntity joinGame(String gameId, String tokenId) throws TokenAlreadyJoinedToGameException, GameAlreadyStartedException {
        GameEntity gameToJoin = gameRepository.getOne(gameId);
        TokenEntity joinerToken = tokenRepository.getOne(tokenId);

        checkIfTokenCanJoinGame(gameToJoin, joinerToken);

        TokenGameEntity tokenGame = createNewTokenGameEntity(gameToJoin, joinerToken, TokenGameEntity.TYPE_JOINER);

        gameToJoin.setGame(Status.ACTIVE);
        gameToJoin.addToken(tokenGame);
        joinerToken.addGame(tokenGame);
        tokenGameRepository.save(tokenGame);
        return gameToJoin;
    }

    private TokenGameEntity createNewTokenGameEntity(GameEntity game, TokenEntity token, String type) {
        return new TokenGameEntity(
                UUID.randomUUID().toString(),
                token,
                game,
                type
        );
    }

    private void checkIfTokenCanJoinGame(GameEntity gameToJoin, TokenEntity joinerToken)
            throws TokenAlreadyJoinedToGameException, GameAlreadyStartedException {
        if (gameToJoin.getTokens().stream()
                .anyMatch(tokenGameEntity -> tokenGameEntity.getToken()
                        .equals(joinerToken)))
            throw new TokenAlreadyJoinedToGameException();

        if (gameToJoin.getGame().equals(Status.ACTIVE)) {
            throw new GameAlreadyStartedException();
        }
    }


    public Stream<GameEntity> all() {
        return gameRepository.findAll().stream().filter(gameEntity -> gameEntity.getGame().equals(Status.OPEN));
    }

    public GameEntity makeMove(Sign sign, String gameId, String tokenId) {
        GameEntity game = gameRepository.getOne(gameId);
        TokenEntity token = tokenRepository.getOne(tokenId);



        String type;
        if (game.isOwner(token)) {
            type = TokenGameEntity.TYPE_OWNER;
            game.setMove(sign);
        } else {
            type = TokenGameEntity.TYPE_JOINER;
            game.setOpponentMove(sign);
        }

        TokenGameEntity tokenGameEntity = getNewTokenGameEntity(game, token, type);
        gameRepository.save(game);
        game.addToken(tokenGameEntity);
        token.addGame(tokenGameEntity);
        tokenGameRepository.save(getNewTokenGameEntity(game, token, type));
        return game;
    }

    private TokenGameEntity getNewTokenGameEntity(GameEntity game, TokenEntity token, String type) {
        return createNewTokenGameEntity(game, token, type);
    }
}
