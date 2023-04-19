package com.example.sprint2.service;

import com.example.sprint2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserService extends JpaRepository<User,Long> {
}
