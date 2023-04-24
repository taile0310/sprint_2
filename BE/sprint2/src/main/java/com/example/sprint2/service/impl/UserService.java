package com.example.sprint2.service.impl;

import com.example.sprint2.model.User;

import com.example.sprint2.repository.IUserRepository;
import com.example.sprint2.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository usersRepository;

    @Override
    public User findUserByUsername(String username) {
        return usersRepository.findUserByUsername(username);
    }

    @Override
    public Page<User> listUser(Pageable pageable) {
        return usersRepository.findAll(pageable);
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

}
