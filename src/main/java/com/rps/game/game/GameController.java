
package com.rps.game.game;


import com.rps.game.token.*;
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
        return toGameStatus(gameService.startGame(tokenId), tokenRepository.getOne(tokenId));
    }

    @GetMapping("/join/{gameId}")
    public GameStatus joinGame(@PathVariable String gameId, @RequestHeader(value = "token", required = true) String tokenId) throws TokenNotFoundException, TokenAllreadyJoinedToGameExeption {
        return toGameStatus(gameService.joinGame(gameId, tokenId),tokenRepository.getOne(tokenId));
    }

    @GetMapping("/status")
    public Game statusGame(@PathVariable String gameId, @RequestHeader(value = "token", required = true) String tokenId) {
        return null;
    }

    @GetMapping()
    public List<Game> listOfAllJoinableGames(@RequestHeader(value = "token", required = false) String tokenId) {
        return gameService.all()
                .map(this::toGame)
                .collect(Collectors.toList());
    }

    @GetMapping("/[id]")
    public Game getGameStatus(@PathVariable String id, @RequestHeader(value = "token", required = true) String tokenId) {
        return null;
    }

    @GetMapping("/move/[sign]")
    public Game makeMove(@PathVariable Sign sign, String gameId, @RequestHeader(value = "token", required = true) String tokenId) {
/*
         switch (sign) {
             case ROCK -> null;
             case PAPER -> null;
             case SCISSORS -> null;
         }
*/

        return null;
    }

    private Game toGame(GameEntity gameEntity) {
        return new Game(
                //gameEntity.getId()
                UUID.randomUUID().toString()
        );

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
    private Status joinerView(Status status){
        switch (status){
            case WIN: return Status.LOSE;
            case LOSE: return Status.WIN;
            default: return status;
        }
    }
}