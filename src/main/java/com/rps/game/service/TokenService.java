package com.rps.game.service;

import com.rps.game.repository.UserRepository;
import com.rps.game.token.Token;
import com.rps.game.user.CreateUser;
import com.rps.game.user.UserEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    UserRepository userRepository;
    Map<String, Token> tokens = new HashMap<>();

    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Token createToken() {
        Token token = Token.create();
        userRepository.save(CreateUser.createUser(token.getId()));
        tokens.put(token.getId(), token);
        return token;
    }

    public Token getTokenById(String tokenId) {
        return tokens.get(tokenId);
    }
}
