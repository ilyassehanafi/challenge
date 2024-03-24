package com.example.demo.service;


import com.example.demo.configuration.JwtUtils;
import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public String login(String username, String password){
        var authToken = new UsernamePasswordAuthenticationToken(username, password);
        var authentication = authenticationManager.authenticate(authToken);

        return JwtUtils.generateToken(((UserDetails)(authentication.getPrincipal())).getUsername());
    }
}
