package com.example.newsreleasemanagementsystem.repository;

import com.example.newsreleasemanagementsystem.domian.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jhlyh
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
