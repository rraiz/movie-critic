package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rraiz.movie_critic.model.Season;

public interface SeasonRepository extends JpaRepository<Season, Integer>{

    
} 