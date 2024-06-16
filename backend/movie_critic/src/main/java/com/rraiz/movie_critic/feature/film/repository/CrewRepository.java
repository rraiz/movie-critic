package com.rraiz.movie_critic.feature.film.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.feature.film.model.entity.Crew;
import com.rraiz.movie_critic.feature.film.model.identifier.CrewId;

@Repository
public interface CrewRepository extends JpaRepository<Crew, CrewId>{

    
} 
