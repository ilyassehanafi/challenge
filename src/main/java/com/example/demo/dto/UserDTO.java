package com.example.demo.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private String firstName;

    private String lastName;

    private Date birthDate;

    private String city;

    private String country;

    private String avatar;

    private String company;

    private String jobPosition;

    private String mobile;

    private String username;

    private String email;

    private String password;

    private String role;

}
