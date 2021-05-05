
package com.rps.game.game;


import com.rps.game.token.*;
import com.rps.game.tokenGame.TokenGameEntity;
import com.rps.game.tokenGame.TokenGameRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GameController {

    GameService gameService;
    TokenService tokenService;
    TokenRepository tokenRepository;
    GameRepository gameRepository;
    TokenGameRepository tokenGameRepository;

    @GetMapping("/start")
    public GameStatus createGame(@RequestHeader(value = "token", required = true) String tokenId) {
        GameEntity game = gameService.startGame(tokenId);
        TokenEntity one = tokenRepository.getOne(tokenId);
        return toGameStatus(game, one);
    }

    @GetMapping("/join/{gameId}")
    public GameStatus joinGame(@PathVariable String gameId, @RequestHeader(value = "token", required = true) String tokenId)
            throws TokenAlreadyJoinedToGameException, GameAlreadyStartedException {
        return toGameStatus(gameService.joinGame(gameId, tokenId), tokenRepository.getOne(tokenId));
    }

    @GetMapping()// /games
    public List<GameStatus> listOfAllJoinableGames(@RequestHeader(value = "token", required = false) String tokenId) {
        return gameService.all()
                .map(gameEntity -> toGameStatus(gameEntity,
                        getOwner(gameEntity)))
                .collect(Collectors.toList());
    }

    @GetMapping("/status")
    public Game statusGame(@RequestHeader(value = "token", required = true) String tokenId) {
        return null;
    }



    @GetMapping("/{id}")
    public GameStatus getGameStatus(@PathVariable String id, @RequestHeader(value = "token", required = false) String tokenId) {
        GameEntity game = gameRepository.getOne(id);
        return toGameStatus(game, getOwner(game));
    }

    @GetMapping("/move/{sign}")
    public GameStatus makeMove(@PathVariable Sign sign, @RequestHeader(value = "token", required = true) String tokenId, String gameId ) {
        return toGameStatus(gameService.makeMove(sign, gameId, tokenId), tokenRepository.getOne(tokenId));
/*
         switch (sign) {
             case ROCK -> null;
             case PAPER -> null;
             case SCISSORS -> null;
         }
*/
    }

    private TokenEntity getOwner(GameEntity gameEntity) {
        return gameEntity.getTokens().stream()
                .filter(tokenGameEntity ->
                        tokenGameEntity.getType()
                                .equals(TokenGameEntity.TYPE_OWNER))
                .findFirst()
                .get()
                .getToken();
    }

    private GameStatus toGameStatus(GameEntity game, TokenEntity token) {//token entity
        if (game.isOwner(token)) {
            return new GameStatus(
                    game.getId(),
                    token.getName(),
                    game.move().map(Enum::name).orElse(""),
                    game.getGame().name(),
                    game.getOpponentName(),
                    game.opponentMove().map(Enum::name).orElse("")
            );
        }
        return new GameStatus(
                game.getId(),
                token.getName(),
                game.opponentMove().map(Enum::name).orElse(""),
                joinerView(game.getGame()).name(),
                game.getOwnerName(),
                game.move().map(Enum::name).orElse("")
        );
    }

    private Status joinerView(Status status) {
        switch (status) {
            case WIN:
                return Status.LOSE;
            case LOSE:
                return Status.WIN;
            default:
                return status;
        }
    }

//    private Game toGame(GameEntity gameEntity) {
//        return new Game(
//                //gameEntity.getId()
//                UUID.randomUUID().toString()
//        );
//
//    }

}
