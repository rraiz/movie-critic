package com.rraiz.movie_critic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rraiz.movie_critic.model.Movie;
import com.rraiz.movie_critic.service.MovieService;
import com.rraiz.movie_critic.util.ResponseUtil;

@RestController
@RequestMapping(path = "/api/v1/movie")
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
