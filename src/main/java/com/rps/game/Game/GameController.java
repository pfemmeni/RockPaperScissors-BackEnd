
package com.rps.game.Game;


import com.rps.game.Exceptions.GameAlreadyStartedException;
import com.rps.game.Exceptions.TokenAlreadyJoinedToGameException;
import com.rps.game.Token.*;
import com.rps.game.TokenGame.TokenGameEntity;
import com.rps.game.TokenGame.TokenGameRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public GameStatus joinGame( @RequestHeader(value = "token", required = true) String tokenId, @PathVariable String gameId)
            throws TokenAlreadyJoinedToGameException, GameAlreadyStartedException {
        return toGameStatus(gameService.joinGame(gameId, tokenId), tokenRepository.getOne(tokenId));
    }

    @GetMapping()
    public List<GameStatus> listOfAllJoinableGames(@RequestHeader(value = "token", required = false) String tokenId) {
        return gameService.allOpenGames()
                .map(gameEntity -> toGameStatus(gameEntity,
                        getOwner(gameEntity)))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GameStatus getGameStatus(@RequestHeader(value = "token", required = false) String tokenId, @PathVariable String id) {
        GameEntity game = gameRepository.getOne(id);
        return toGameStatus(game, tokenId == null ? getOwner(game) : tokenRepository.getOne(tokenId));
    }

    @GetMapping("/move/{sign}")
    public GameStatus makeMove(@PathVariable Sign sign, @RequestHeader(value = "token", required = true) String tokenId, String gameId) {
        return toGameStatus(gameService.makeMove(sign, gameId, tokenId), tokenRepository.getOne(tokenId));
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

}
