package com.rps.game.token;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TokenController {

    TokenService tokenService;

    @GetMapping("/auth/token")
    public String createNewToken() {
        return tokenService.createToken().getId();
    }

    @PostMapping("user/name")
    public Token setName(@RequestBody SetName setName,
                        @RequestHeader(value = "token", required = true) String tokenId) throws TokenNotFoundException {
        return toToken(tokenService.setName(setName, tokenId));
    }

    public Token toToken(TokenEntity tokenEntity){
        Token token = new Token(tokenEntity.getId(),tokenEntity.getName());
        return token;
    }
}
