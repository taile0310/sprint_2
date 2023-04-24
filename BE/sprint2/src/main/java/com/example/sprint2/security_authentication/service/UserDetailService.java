package com.example.sprint2.security_authentication.service;


import com.example.sprint2.model.User;
import com.example.sprint2.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    IUserRepository userRepository;

    @Override
    @Transactional
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)  {
        User user = userRepository.findUserByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
        return UserDetails.build(user);
    }
}