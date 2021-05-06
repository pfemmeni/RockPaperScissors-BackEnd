package com.rps.game;

import com.rps.game.Exceptions.OneGameAtTheTimeAllowedException;
import com.rps.game.Game.GameController;
import com.rps.game.Game.GameStatus;
import com.rps.game.Token.TokenController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("integrationtest")
class RockPaperScissorApplicationTests {

    @Autowired
    GameController gameController;
    @Autowired
    TokenController tokenController;
    @Test
    void test_token() {
        String token = tokenController.createNewToken();

        assertNotNull(token);
    }

    @Test
    @Transactional
    void name() throws OneGameAtTheTimeAllowedException {
        // Given
        String tokenId = tokenController.createNewToken();

        // When
        GameStatus game = gameController.createGame(tokenId);

        // Then
        assertNotNull(game);
    }
}
