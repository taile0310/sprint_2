package com.example.sprint2.repository;

import com.example.sprint2.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRoleRepository extends JpaRepository<UserRole, Long> {
}
