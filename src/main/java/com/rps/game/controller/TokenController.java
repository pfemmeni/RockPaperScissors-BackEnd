package com.rps.game.controller;

import com.rps.game.service.TokenService;
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
}
