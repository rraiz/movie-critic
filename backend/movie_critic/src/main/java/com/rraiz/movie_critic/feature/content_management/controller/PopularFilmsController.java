package com.rraiz.movie_critic.feature.content_management.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rraiz.movie_critic.feature.content_management.dto.PopularFilms.FilmDTO;
import com.rraiz.movie_critic.feature.content_management.service.PopularFilmsService;
import com.rraiz.movie_critic.feature.film.model.entity.Movie;
import com.rraiz.movie_critic.feature.film.model.entity.TvShow;
import com.rraiz.movie_critic.util.ResponseUtil;

@RestController
@RequestMapping(path = "/api/v1/popular")
public class PopularFilmsController {

    private final PopularFilmsService popularFilmsService;

    public PopularFilmsController(PopularFilmsService popularFilmsService) {
        this.popularFilmsService = popularFilmsService;
    }

    @GetMapping("/tv")
    public ResponseEntity<Set<FilmDTO>> getPopularTvShows() {
        Set<TvShow> films = popularFilmsService.getPopularFilms(1);
        Set<FilmDTO> filmDTOs = films.stream()
                                      .map(FilmDTO::new)  // Using the constructor
                                      .collect(Collectors.toSet());
        return ResponseUtil.createResponseEntity(filmDTOs);    }

    @GetMapping("/movies")
    public ResponseEntity<Set<FilmDTO>> getPopularMovies() {
        Set<Movie> films = popularFilmsService.getPopularFilms(0);
        Set<FilmDTO> filmDTOs = films.stream()
                                      .map(FilmDTO::new)  // Using the constructor
                                      .collect(Collectors.toSet());
        return ResponseUtil.createResponseEntity(filmDTOs);
    }
    

}
