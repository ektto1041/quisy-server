package com.karpo.quisy.services;

import com.karpo.quisy.entities.User;
import com.karpo.quisy.exceptions.NotFoundUserException;
import com.karpo.quisy.helpers.UserBuilder;
import com.karpo.quisy.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
    private UserBuilder userBuilder = new UserBuilder();

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Get User by Id Success")
    void getUserById() {
        // Given
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userBuilder.one(7)));

        // When
        User foundUser = userService.getUserById((long) 2);

        // Then
        assertEquals(7, foundUser.getUserId());
    }

    @Test
    @DisplayName("Get User by Id Failed: Not Found User")
    void getUserByIdNotFoundUser() {
        // Given
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundUserException.class, () -> {
            userService.getUserById((long) 2);
        });
    }
}