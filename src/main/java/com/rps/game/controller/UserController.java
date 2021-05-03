package com.rps.game.controller;

import com.rps.game.token.TokenService;
import com.rps.game.token.SetName;
import com.rps.game.token.TokenNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {


    TokenService tokenService;
    UserService userService;

    @PostMapping("/name")
    public User setName(@RequestBody SetName setName,
                        @RequestHeader(value = "token", required = true) String tokenId) throws TokenNotFoundException {
        return toUser(userService.setName(setName, tokenId));
    }

    private User toUser(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getName());
    }
}