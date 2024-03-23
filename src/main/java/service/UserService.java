package service;

import com.github.javafaker.Faker;
import dto.UserDTO;
import entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserServiceInterface{

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDTO> generateUsers(int count) {
        List<UserDTO> listUsers = new ArrayList<>();
        int i =0;
        while(i<count){
            listUsers.add(generateUser());
            i++;
        }

        return listUsers;
    }
    private UserDTO generateUser(){
        Faker faker = new Faker();
        return UserDTO.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .birthDate(faker.date().birthday())
                .city(faker.address().city())
                .country(faker.address().country())
                .avatar(faker.avatar().image())
                .company(faker.company().name())
                .jobPosition(faker.job().position())
                .mobile(faker.phoneNumber().phoneNumber())
                .email(faker.internet().emailAddress())
                //.password()
                //.role()
                .build();
    }
}
