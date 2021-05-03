package com.rps.game.token;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    TokenRepository tokenRepository;
    Map<String, Token> tokens = new HashMap<>();

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public TokenEntity createToken() {
        TokenEntity token = TokenEntity.create();
        tokenRepository.save(token);
        return token;
    }

    public TokenEntity setName(SetName setName, String id) throws TokenNotFoundException {
        return tokenRepository.findById(id)
                .map(tokenEntity -> {
                    if (setName.getName() != null)
                        tokenEntity.setName(setName.getName());
                    return tokenEntity;
                })
                .orElseThrow(TokenNotFoundException::new);
    }

    public Token getTokenById(String tokenId) {
        return tokens.get(tokenId);
    }
}
