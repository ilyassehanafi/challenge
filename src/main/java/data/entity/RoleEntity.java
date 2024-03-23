package data.entity;

import data.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}
