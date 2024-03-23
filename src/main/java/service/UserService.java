package service;

import dto.UserDTO;
import data.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAllUsers();

    List<UserDTO> generateUsers(int count);
}
