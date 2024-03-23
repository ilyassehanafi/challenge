package service;

import dto.UserDTO;
import entity.UserEntity;

import java.util.List;

public interface UserServiceInterface {
    public List<UserEntity> findAllUsers();

    public List<UserDTO> generateUsers(int count);
}
