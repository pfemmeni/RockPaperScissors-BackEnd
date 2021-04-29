package com.rps.game.controller;

import com.rps.game.service.TokenService;
import com.rps.game.service.UserService;
import com.rps.game.user.SetName;
import com.rps.game.user.User;
import com.rps.game.user.UserEntity;
import com.rps.game.user.UserNotFoundException;
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
                        @RequestHeader(value = "token", required = true) String tokenId) throws UserNotFoundException {
        //Token token = tokenService.getTokenById(tokenId);
        return toUser(userService.setName(setName, tokenId));
    }

    private User toUser(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getName());
    }
}