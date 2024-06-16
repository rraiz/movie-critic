package com.rraiz.movie_critic.feature.film.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rraiz.movie_critic.feature.film.model.entity.Movie;
import com.rraiz.movie_critic.feature.film.service.MovieService;
import com.rraiz.movie_critic.util.ResponseUtil;

@RestController
@RequestMapping(path = "/api/v1/movie")
@CrossOrigin(origins = "http://localhost:5173")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("movieId") int movieId) throws Exception {
        Movie movie = movieService.getMovieDetails(movieId);
        return ResponseUtil.createResponseEntity(movie);
    }

    
}
