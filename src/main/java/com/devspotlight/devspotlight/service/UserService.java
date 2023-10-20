package com.devspotlight.devspotlight.service;

import com.devspotlight.devspotlight.model.User;
import com.devspotlight.devspotlight.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsernameOrEmail(username,username);

        if(user == null){
            throw  new UsernameNotFoundException("User not exist by Username");
        }
        return null;
    }
}
