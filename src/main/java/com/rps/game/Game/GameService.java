package com.rps.game.Game;

import com.rps.game.Exceptions.GameAlreadyStartedException;
import com.rps.game.Exceptions.OneGameAtTheTimeAllowedException;
import com.rps.game.Exceptions.TokenAlreadyJoinedToGameException;
import com.rps.game.Token.TokenEntity;
import com.rps.game.TokenGame.TokenGameEntity;
import com.rps.game.TokenGame.TokenGameRepository;
import com.rps.game.Token.TokenRepository;
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

    public GameEntity startGame(String tokenId) throws OneGameAtTheTimeAllowedException {
        GameEntity game = createGame();
        TokenEntity token = tokenRepository.getOne(tokenId);

        TokenGameEntity tokenGame = createNewTokenGameEntity(game, token, TokenGameEntity.TYPE_OWNER);

        game.setGame(Status.OPEN);
        game.addToken(tokenGame);
        if(token.getGames().size()==1){
            throw new OneGameAtTheTimeAllowedException();
        }
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

    private TokenGameEntity createNewTokenGameEntity(GameEntity game, TokenEntity token, String type) {
        return new TokenGameEntity(
                UUID.randomUUID().toString(),
                token,
                game,
                type
        );
    }


    public Stream<GameEntity> allOpenGames() {
        return gameRepository.findAll().stream().filter(gameEntity -> gameEntity.getGame().equals(Status.OPEN));
    }


    public GameEntity makeMove(Sign sign,  String tokenId) {
        TokenEntity token = tokenRepository.getOne(tokenId);
        GameEntity game = token.getGames().stream().findFirst().get().getGame();

        String type = getType(sign, game, token);

        TokenGameEntity tokenGameEntity = getNewTokenGameEntity(game, token, type);

        gameResultCalculation(sign, game, token);

        gameRepository.save(game);
        game.addToken(tokenGameEntity);
        token.addGame(tokenGameEntity);
        tokenGameRepository.save(getNewTokenGameEntity(game, token, type));
        return game;
    }

    private String getType(Sign sign, GameEntity game, TokenEntity token) {
        String type;
        if (game.isOwner(token)) {
            type = TokenGameEntity.TYPE_OWNER;
            game.setMove(sign);
        } else {
            type = TokenGameEntity.TYPE_JOINER;
            game.setOpponentMove(sign);
        }
        return type;
    }

    private void gameResultCalculation(Sign sign, GameEntity game, TokenEntity token) {
        if (checkIfAllPlayersMadeMove(game.getId())) {
            if (game.isOwner(token)) {
                game.setGame(gameResult(sign, game.getOpponentMove()));
            } else {
                game.setGame(gameResult(sign, game.getMove()));
            }
        }
    }

    private boolean checkIfAllPlayersMadeMove(String gameId) {
        GameEntity game = gameRepository.getOne(gameId);
        return game.getMove() != null || game.getOpponentMove() != null;
    }

    private Status gameResult(Sign sign, Sign opponentSign) {
        switch (sign) {
            case ROCK: {
                if (opponentSign.equals(Sign.ROCK))
                    return Status.DRAW;
                if (opponentSign.equals(Sign.PAPER))
                    return Status.LOSE;
                if (opponentSign.equals(Sign.SCISSORS))
                    return Status.WIN;
            }
            case PAPER: {
                if (opponentSign.equals(Sign.PAPER))
                    return Status.DRAW;
                if (opponentSign.equals(Sign.SCISSORS))
                    return Status.LOSE;
                if (opponentSign.equals(Sign.ROCK))
                    return Status.WIN;
            }
            case SCISSORS: {
                if (opponentSign.equals(Sign.SCISSORS))
                    return Status.DRAW;
                if (opponentSign.equals(Sign.ROCK))
                    return Status.LOSE;
                if (opponentSign.equals(Sign.PAPER))
                    return Status.WIN;
            }
        }
        return Status.NONE;
    }


    private TokenGameEntity getNewTokenGameEntity(GameEntity game, TokenEntity token, String type) {
        return createNewTokenGameEntity(game, token, type);
    }
}
