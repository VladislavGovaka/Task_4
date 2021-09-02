package com.Itransition.LaricSecurity.entity;

import com.Itransition.LaricSecurity.entity.User;
import com.Itransition.LaricSecurity.repositories.UserRepository;
import com.Itransition.LaricSecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.Date;

@Component
public class LastData implements ApplicationListener<AuthenticationSuccessEvent>
{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent evt) {

        String loginName = evt.getAuthentication().getName();
        User user = userRepository.findByUsername(loginName);
        user.setLastLoginDate(new Date());
        userRepository.save(user);
    }
}
