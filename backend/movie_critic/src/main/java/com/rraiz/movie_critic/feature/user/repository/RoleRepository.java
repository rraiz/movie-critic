package com.rraiz.movie_critic.feature.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.feature.user.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

    Optional<Role> findByAuthority(String name);

} 
