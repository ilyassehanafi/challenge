package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String city;

    private String country;

    //link to an image
    private String avatar;

    private String company;

    private String jobPosition;

    private String mobile;

    private String username;

    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<RoleEntity> roles;
}
