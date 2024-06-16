package com.rraiz.movie_critic.feature.film.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.feature.film.model.entity.Film;
import com.rraiz.movie_critic.feature.film.model.identifier.FilmId;

@Repository
public interface FilmRepository extends JpaRepository<Film, FilmId>{

    
} 