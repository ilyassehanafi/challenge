package com.example.demo.data.entity;

import com.example.demo.data.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}
