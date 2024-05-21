package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.model.Created;
import com.rraiz.movie_critic.model.CreatedId;

@Repository
public interface CreatedRepository extends JpaRepository<Created, CreatedId>{

    
} 
