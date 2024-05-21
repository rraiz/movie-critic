package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rraiz.movie_critic.model.Created;
import com.rraiz.movie_critic.model.CreatedId;

public interface CreatedRepository extends JpaRepository<Created, CreatedId>{

    
} 
