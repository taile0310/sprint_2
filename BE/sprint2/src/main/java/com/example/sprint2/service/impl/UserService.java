package com.example.sprint2.service.impl;

import com.example.sprint2.model.Role;
import com.example.sprint2.model.User;

import com.example.sprint2.model.UserRole;
import com.example.sprint2.repository.IRoleRepository;
import com.example.sprint2.repository.IUserRepository;
import com.example.sprint2.repository.IUserRoleRepository;
import com.example.sprint2.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository usersRepository;

    @Autowired
    private IUserRoleRepository userRoleRepository;
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public User findUserByUsername(String username) {
        return usersRepository.findUserByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        usersRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        usersRepository.deleteById(id);
    }

    @Override
    public Page<User> customerPage(Pageable pageable) {
        return usersRepository.customerPage(pageable);
    }

    @Override
    public Page<User> employeePage(Pageable pageable) {
        return usersRepository.employeePage(pageable);
    }

    @Override
    public User findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public User registerUser(User user) {
        return usersRepository.save(user);
    }

    @Override
    public UserRole setRoleOfUser(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public Role findByIdRole(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserRole> userRoleList() {
        return userRoleRepository.findAll();
    }


}
