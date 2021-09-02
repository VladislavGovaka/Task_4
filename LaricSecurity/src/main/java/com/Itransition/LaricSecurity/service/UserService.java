package com.Itransition.LaricSecurity.service;

import com.Itransition.LaricSecurity.entity.Role;
import com.Itransition.LaricSecurity.entity.User;
import com.Itransition.LaricSecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setBlock(true);
        user.setRoles(Collections.singleton(Role.USER));

        Date dateRegistration = new Date();
        SimpleDateFormat formatForDate = new SimpleDateFormat("dd.MM.yyyy hh:mm ", Locale.UK);

        user.setDataRegistration(formatForDate.format(dateRegistration));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return true;
    }


    public User getAuthenticationUser(){                        //получаю юзера по сессии
        User user = null;
        try{
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e){

            return null;

        }
        return user;
    }
}