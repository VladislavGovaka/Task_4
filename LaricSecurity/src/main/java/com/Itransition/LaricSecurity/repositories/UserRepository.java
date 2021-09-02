package com.Itransition.LaricSecurity.repositories;

import com.Itransition.LaricSecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
