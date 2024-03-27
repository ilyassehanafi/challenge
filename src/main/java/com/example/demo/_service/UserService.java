package com.example.demo._service;

import com.example.demo.dto.CreationDetail;
import com.example.demo.dto.UserDTO;
import com.example.demo.data.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserEntity> findAllUsers();

    List<UserDTO> generateUsers(int count) ;

    CreationDetail saveUsers(MultipartFile file) throws IOException;

    UserDTO getActualUser();

    UserDTO viewProfil(String username) throws Exception;
}
