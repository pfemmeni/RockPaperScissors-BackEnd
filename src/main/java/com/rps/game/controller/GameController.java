
package com.rps.game.controller;


import com.rps.game.game.GameStatus;
import com.rps.game.game.Status;
import com.rps.game.repository.UserRepository;
import com.rps.game.service.GameService;
import com.rps.game.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GameController {

    GameService gameService;
    TokenService tokenService;
    UserRepository userRepository;



    @GetMapping("/start")//
    public GameStatus createGame(@RequestHeader(value = "token", required = true) String tokenId) {
        return gameService.createNewGame(tokenId);


    }


/*
    @GetMapping("/join/{gameId}")
    public Game joinGame(@PathVariable String gameId, @RequestHeader(value = "token", required = false) String tokenId) {
        Token token = tokenService.getTokenById(tokenId);
        return null;
    }

    @GetMapping("/status")
    public Game statusGame(@PathVariable String gameId, @RequestHeader(value = "token", required = false) String tokenId) {
        Token token = tokenService.getTokenById(tokenId);
        return null;
    }

    @GetMapping()
    public Game listOfAllJoinableGames(@RequestHeader(value = "token", required = false) String tokenId) {
        Token token = tokenService.getTokenById(tokenId);
        return null;
    }

    @GetMapping("/[id]")
    public Game getGameStatus(@PathVariable String id, @RequestHeader(value = "token", required = false) String tokenId) {
        Token token = tokenService.getTokenById(tokenId);
        return null;
    }

    @GetMapping("/move/[sign]")
    public Game makeMove(@PathVariable Signs sign, String gameId, @RequestHeader(value = "token", required = false) String tokenId) {
        Token token = tokenService.getTokenById(tokenId);
        return null;
    }

*/
}