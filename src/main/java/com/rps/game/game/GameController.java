
package com.rps.game.game;


import com.rps.game.repository.UserRepository;
import com.rps.game.token.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GameController {

    GameService gameService;
    TokenService tokenService;
    UserRepository userRepository;
    GameRepository gameRepository;


    @GetMapping("/start")//
    public GameStatus createGame(@RequestHeader(value = "token", required = true) String tokenId) {
        return gameService.startGame(tokenId);
    }


    @GetMapping("/join/{gameId}")
    public Optional<GameEntity> joinGame(@PathVariable String gameId, @RequestHeader(value = "token", required = true) String tokenId) {
        return gameRepository.findById(gameId);
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
        return new Game(UUID.randomUUID().toString()
        );
    }


}