package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.model.Film;
import com.rraiz.movie_critic.model.FilmId;

@Repository
public interface FilmRepository extends JpaRepository<Film, FilmId>{

    
} 