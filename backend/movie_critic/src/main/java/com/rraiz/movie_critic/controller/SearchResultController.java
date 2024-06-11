package com.rraiz.movie_critic.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rraiz.movie_critic.model.Film;
import com.rraiz.movie_critic.model.Movie;
import com.rraiz.movie_critic.model.TvShow;
import com.rraiz.movie_critic.service.MovieService;
import com.rraiz.movie_critic.service.TvShowService;
import com.rraiz.movie_critic.util.ResponseUtil;

@RestController
@RequestMapping(path = "/api/v1/search")
@CrossOrigin(origins = "http://localhost:5173")
public class SearchResultController {

    private final MovieService movieService;
    private final TvShowService tvShowService;

    public SearchResultController(MovieService movieService, TvShowService tvShowService) {
        this.movieService = movieService;
        this.tvShowService = tvShowService;
    }


    @GetMapping("/movie/{movieName}")
    public ResponseEntity<List<Movie>> getMovieName(@PathVariable("movieName") String movieName){
        List<Movie> movies =  movieService.getMoviesByName(movieName);
        Collections.sort(movies, Comparator.comparing(Film::getPopularity).reversed());
        return ResponseUtil.createResponseEntity(movies);
    }

    @GetMapping("/tv/{tvShowName}")
    public ResponseEntity<List<TvShow>> getTvShowName(@PathVariable("tvShowName") String tvShowName){
        List<TvShow> tvShows = tvShowService.getTvShowsByName(tvShowName);
        Collections.sort(tvShows, Comparator.comparing(Film::getPopularity).reversed());
        return ResponseUtil.createResponseEntity(tvShows);
    }

    @GetMapping("/film/{filmName}")
    public ResponseEntity<List<Film>> getFilmNames(@PathVariable("filmName") String filmName){
        List<Movie> movies =  movieService.getMoviesByName(filmName);
        List<TvShow> tvShows = tvShowService.getTvShowsByName(filmName);
        List<Film> films = new ArrayList<>();

        films.addAll(movies);
        films.addAll(tvShows);
        Collections.sort(films, Comparator.comparing(Film::getPopularity).reversed());

        return ResponseUtil.createResponseEntity(films);
    }



    
}
