package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rraiz.movie_critic.model.Film;
import com.rraiz.movie_critic.model.FilmId;

public interface FilmRepository extends JpaRepository<Film, FilmId>{

    
} 