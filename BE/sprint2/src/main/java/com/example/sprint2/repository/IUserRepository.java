package com.example.sprint2.repository;

import com.example.sprint2.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    @Query(value = "select  * from users\n" +
            "join user_role ur on users.id = ur.user_id\n" +
            "where ur.role_id = 3", nativeQuery = true)
    Page<User> customerPage(Pageable pageable);
    @Query(value = "select  * from users\n" +
            "join user_role ur on users.id = ur.user_id\n" +
            "where ur.role_id < 3", nativeQuery = true)
    Page<User> employeePage(Pageable pageable);

}
