package com.karpo.quisy.services;

import com.karpo.quisy.entities.User;
import com.karpo.quisy.exceptions.NotFoundUserException;
import com.karpo.quisy.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundUserException("UserId: " + id + " 유저를 찾을 수 없습니다"));
    }
}
