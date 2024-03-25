package com.example.demo.service;

import com.example.demo.configuration.JwtUtils;
import com.example.demo.data.entity.RoleEntity;
import com.example.demo.data.entity.UserEntity;
import com.example.demo.data.enums.RoleType;
import com.example.demo.data.repository.RoleRepository;
import com.example.demo.data.repository.UserRepository;
import com.example.demo.dto.CreationDetail;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.example.demo.dto.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final int COUNT_WARN_VALUE = 1000;

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    public List<UserDTO> generateUsers(int count) {
        if (count>COUNT_WARN_VALUE)
            Logger.getAnonymousLogger().warning("File generation will take longer time to complete!");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<UserDTO> userDTOList = IntStream.range(0, count)
                .mapToObj(i -> generateUser())
                .collect(Collectors.toList());
        stopWatch.stop();
        //not working
        Logger.getAnonymousLogger().info("total Time for generating Users : " + stopWatch.getTotalTimeSeconds()/60 + " Mins");
        return userDTOList;
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

    @SneakyThrows
    @Override
    @Transactional
    public CreationDetail saveUsers(MultipartFile file) {

        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        List<UserDTO> userDTOList = objectMapper.readValue(br.readLine(), new TypeReference<>() {});
        for (UserDTO userDTO: userDTOList) {
            System.out.println(userDTO);
        }
        List<UserEntity> usersEnityList = this.transformDtoToEntity(userDTOList);
        return saveAllUsers(usersEnityList);
    }

    @Override
    public UserDTO getActualUser(HttpServletRequest http) {
        String token = JwtUtils.getTokenFromRequest(http).get();
        String email = JwtUtils.getUsernameFromToken(token).get();
        Optional<UserEntity> user = userRepository.findUserEntityByEmail(email);
        return transformEntityToDto(user.get());

    }

    private List<UserEntity> transformDtoToEntity(List<UserDTO> usersList) {
        return usersList.stream().map(userDTO -> {
            String role = userDTO.getRole();
            RoleEntity roleEntity = RoleEntity.builder().roleType(RoleType.valueOf(role)).build();
            List<RoleEntity> roleEntities = Collections.singletonList(roleEntity);
            return  UserEntity.builder()
                    .firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .birthDate(userDTO.getBirthDate())
                    .city(userDTO.getCity())
                    .country(userDTO.getCountry())
                    .avatar(userDTO.getAvatar())
                    .company(userDTO.getCompany())
                    .jobPosition(userDTO.getJobPosition())
                    .mobile(userDTO.getMobile())
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .roles(roleEntities)
                    .build();
        }).collect(Collectors.toList());
    }

    private UserDTO transformEntityToDto(UserEntity userEntity) {
        return  UserDTO.builder()
                    .firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName())
                    .birthDate(userEntity.getBirthDate())
                    .city(userEntity.getCity())
                    .country(userEntity.getCountry())
                    .avatar(userEntity.getAvatar())
                    .company(userEntity.getCompany())
                    .jobPosition(userEntity.getJobPosition())
                    .mobile(userEntity.getMobile())
                    .email(userEntity.getEmail())
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .role(userEntity.getRoles().toString())
                    .build();
    }

    private CreationDetail saveAllUsers(List<UserEntity> users){
        CreationDetail creationDetail = new CreationDetail();
        creationDetail.setTotalRecordsCount(users.size());
        for(UserEntity user : users){
            if(userRepository.existsByUsernameOrEmail(user.getUsername(),user.getEmail())){
                creationDetail.incrementRecordsNotSaved();
            }else{
                user.setPassword(this.passwordEncoder.encode(user.getPassword()));
                Collection<RoleEntity> roles = user.getRoles();
                for (RoleEntity role: roles) {
                    if(roleRepository.findByRoleType(role.getRoleType()) == null){
                        roleRepository.save(role);
                    }else{
                        user.setRoles(Collections.
                                singletonList(roleRepository.findByRoleType(role.getRoleType())));
                    }
                }
                this.saveUser(user);
                creationDetail.incrementRecordsSaved();
            }
        }
        return creationDetail;
    }

    public UserEntity saveUser(UserEntity user){
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityByUsername = userRepository.findUserEntityByUsername(login);

        return userEntityByUsername
                .or(() -> userRepository.findUserEntityByEmail(login))
                .orElseThrow(() -> new UsernameNotFoundException("username or email not found"));
    }


}
