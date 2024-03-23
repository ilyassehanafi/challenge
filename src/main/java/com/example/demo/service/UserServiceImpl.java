package com.example.demo.service;

import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.enums.RoleType;
import com.example.demo.data.repository.UserRepository;
import com.github.javafaker.Faker;
import com.example.demo.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    // Number of threads in the thread pool
    private static final int THREAD_POOL_SIZE = 10;

    public List<UserDTO> generateUsers(int count) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Future<UserDTO>> futures = new ArrayList<>();

        try {
            for (int i = 0; i < count; i++) {
                Future<UserDTO> future = executor.submit(this::generateUser);
                futures.add(future);
            }

            List<UserDTO> generatedUsers = new ArrayList<>();
            for (Future<UserDTO> future : futures) {
                generatedUsers.add(future.get());
            }

            return generatedUsers;
        } finally {
            executor.shutdown();
        }
    }
    private UserDTO generateUser(){
        Faker faker = new Faker();
        RoleType[] roles = RoleType.values();
        Random random = new Random();
        String role = roles[random.nextInt(roles.length)].name();
        return UserDTO.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .birthDate(faker.date().birthday())
                .city(faker.address().city())
                .country(faker.address().countryCode())
                .avatar(faker.avatar().image())
                .company(faker.company().name())
                .jobPosition(faker.job().position())
                .mobile(faker.phoneNumber().phoneNumber())
                .email(faker.internet().emailAddress())
                .username(faker.name().username())
                .password(faker.internet().password(6,10))
                .role(role)
                .build();
    }
}
