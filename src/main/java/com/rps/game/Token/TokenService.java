package com.rps.game.Token;

import com.rps.game.Exceptions.TokenNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    TokenRepository tokenRepository;
//    Map<String, Token> tokens = new HashMap<>();

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public TokenEntity createToken() {
        TokenEntity token = TokenEntity.create();
        tokenRepository.save(token);
        return token;
    }

    public TokenEntity setName(SetName setName, String id) throws TokenNotFoundException {
        TokenEntity tokenToSetName = tokenRepository.findById(id)
                .map(tokenEntity -> {
                    if (setName.getName() != null)
                        tokenEntity.setName(setName.getName());
                    return tokenEntity;
                })
                .orElseThrow(TokenNotFoundException::new);
        tokenRepository.save(tokenToSetName);
        return tokenToSetName;
    }

//    public Token getTokenById(String tokenId) {
//        return tokens.get(tokenId);
//    }

}

