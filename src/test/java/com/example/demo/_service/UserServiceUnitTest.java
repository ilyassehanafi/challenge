package com.example.demo._service;

import com.example.demo.data.entity.RoleEntity;
import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.enums.RoleType;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.dto.CreationDetail;
import com.example.demo.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
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
