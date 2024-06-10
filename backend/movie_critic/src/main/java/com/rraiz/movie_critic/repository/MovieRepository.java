package com.rraiz.movie_critic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rraiz.movie_critic.model.FilmId;
import com.rraiz.movie_critic.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, FilmId>{
    
    List<Movie> findByNameContaining(String name);
    
} 