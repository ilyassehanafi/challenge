package com.example.demo.data.repository;

import com.example.demo.data.entity.RoleEntity;
import com.example.demo.data.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
	RoleEntity findByRoleType(RoleType roleType);
}
