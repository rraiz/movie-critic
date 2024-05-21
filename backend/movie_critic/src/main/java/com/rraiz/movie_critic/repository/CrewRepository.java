package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.model.Crew;
import com.rraiz.movie_critic.model.CrewId;

@Repository
public interface CrewRepository extends JpaRepository<Crew, CrewId>{

    
} 
