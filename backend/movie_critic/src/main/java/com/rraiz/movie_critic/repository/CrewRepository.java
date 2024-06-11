package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.model.entity.Crew;
import com.rraiz.movie_critic.model.identifier.CrewId;

@Repository
public interface CrewRepository extends JpaRepository<Crew, CrewId>{

    
} 
