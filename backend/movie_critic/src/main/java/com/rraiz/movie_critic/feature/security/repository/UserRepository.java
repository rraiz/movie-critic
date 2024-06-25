package com.rraiz.movie_critic.feature.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.feature.security.model.ApplicationUser;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Integer>{

    Optional<ApplicationUser> findByUsername(String username);

    Optional<ApplicationUser> findByEmail(String email);

} 
