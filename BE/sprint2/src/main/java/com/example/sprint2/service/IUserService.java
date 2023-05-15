package com.example.sprint2.service;



import com.example.sprint2.model.Role;
import com.example.sprint2.model.User;
import com.example.sprint2.model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUserService {
    User findUserByUsername(String username);

    void saveUser(User user);
    User findById( Long id);
    void deleteById(@Param("id") Long id);

    Page<User> customerPage(Pageable pageable);

    Page<User> employeePage(Pageable pageable);

    User findByUsername(String username);
    User findByEmail(String email);

    User registerUser(User user);

    UserRole setRoleOfUser(UserRole userRole);
    Role findByIdRole(Long id);
    List<UserRole> userRoleList();

}

