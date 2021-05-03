
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
        return toGameStatus(gameService.startGame(tokenId), tokenId);
    }

    @GetMapping("/join/{gameId}")
    public GameStatus joinGame(@PathVariable String gameId, @RequestHeader(value = "token", required = true) String tokenId) throws TokenNotFoundException, TokenAllreadyJoinedToGameExeption {
        return toGameStatus(gameService.joinGame(gameId, tokenId), tokenId);
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
        return new Game(gameEntity.getId()
        );

    }
    private GameStatus toGameStatus(GameEntity game, String tokenId) {
        TokenEntity player = tokenGameRepository.findAll().stream()
                .filter(tokenGameEntity -> tokenGameEntity.getGame().equals(game))
                .filter(tokenGameEntity -> tokenGameEntity.getToken().getId().equals(tokenId)).findAny().get().getToken();

        TokenEntity opponent = tokenGameRepository.findAll().stream()
                .filter(tokenGameEntity -> tokenGameEntity.getGame().equals(game))
                .filter(tokenGameEntity -> !tokenGameEntity.getToken().getId().equals(tokenId)).findAny().get().getToken();

        return new GameStatus(
                game.getId(),
                player.getName(),
                game.getMove().toString(),
                game.getGame().toString(),
                opponent.getName(),
                game.getOpponentMove().toString()

        );
    }

}