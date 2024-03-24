package com.example.demo.service;

import com.example.demo.dto.CreationDetail;
import com.example.demo.dto.UserDTO;
import com.example.demo.data.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService {
    List<UserEntity> findAllUsers();

    List<UserDTO> generateUsers(int count) throws InterruptedException, ExecutionException;

    CreationDetail saveUsers(MultipartFile file) throws IOException;
}
