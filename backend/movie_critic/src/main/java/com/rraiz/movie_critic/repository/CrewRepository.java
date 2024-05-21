package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rraiz.movie_critic.model.Crew;
import com.rraiz.movie_critic.model.CrewId;

public interface CrewRepository extends JpaRepository<Crew, CrewId>{

    
} 
