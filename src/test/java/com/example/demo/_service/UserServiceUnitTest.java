package com.example.demo._service;

import com.example.demo.data.repository.UserRepository;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;


class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateUsers() {
        int count = 5;
        List<UserDTO> result = userService.generateUsers(count);

        assertThat(result)
            .hasSize(count)
                .first()
                .isInstanceOf(UserDTO.class)
                .hasFieldOrProperty("firstName")
                .hasFieldOrProperty("lastName");
    }

}
