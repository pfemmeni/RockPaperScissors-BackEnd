package com.rps.game.service;

import com.rps.game.repository.UserRepository;
import com.rps.game.user.SetName;
import com.rps.game.user.UserEntity;
import com.rps.game.user.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;
    TokenService tokenService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity setName(SetName setName, String id) throws UserNotFoundException {
       return userRepository.findById(id)
                .map(userEntity -> {
                    if (setName.getName() != null)
                        userEntity.setName(setName.getName());
                    return userEntity;
                })
                .orElseThrow(UserNotFoundException::new);
    }
}
