package com.example.sprint2.service;


import com.example.sprint2.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface IUserService {
    User findUserByUsername(String username);

    Page<User> listUser(Pageable pageable);


    void saveUser(User user);
    User findById( Long id);

    void deleteById(@Param("id") Long id);

}
