package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rraiz.movie_critic.model.Produced;
import com.rraiz.movie_critic.model.ProducedId;

public interface ProducedRepository extends JpaRepository<Produced, ProducedId>{

    
} 