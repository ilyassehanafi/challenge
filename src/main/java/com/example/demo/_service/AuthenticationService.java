package com.example.demo._service;


import com.example.demo.configuration.JwtUtils;
import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;

    public String login(String login, String password){
        Optional<UserEntity> userEntityByUsername = userRepository.findUserEntityByUsername(login);

        UserEntity userEntity = userEntityByUsername
                .or(() -> userRepository.findUserEntityByEmail(login))
                .orElseThrow(() -> new UsernameNotFoundException("username or email not found"));

        var authToken = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), password);
        authenticationManager.authenticate(authToken);
        return JwtUtils.generateToken(userEntity.getEmail());
    }
}
