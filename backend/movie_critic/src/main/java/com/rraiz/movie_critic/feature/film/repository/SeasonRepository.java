package com.rraiz.movie_critic.feature.film.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.feature.film.model.entity.Season;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Integer>{

    
} 