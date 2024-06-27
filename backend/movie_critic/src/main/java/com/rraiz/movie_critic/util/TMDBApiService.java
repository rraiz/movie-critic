package com.rraiz.movie_critic.util;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.feature.film.api_external.MovieApiService;
import com.rraiz.movie_critic.feature.film.api_external.PersonApiService;
import com.rraiz.movie_critic.feature.film.api_external.TvShowApiService;

import com.rraiz.movie_critic.feature.film.model.entity.Movie;
import com.rraiz.movie_critic.feature.film.model.entity.Person;
import com.rraiz.movie_critic.feature.film.model.entity.TvShow;

@Service
public class TMDBApiService {

    private final MovieApiService movieApiService;
    private final TvShowApiService tvShowApiService;
    private final PersonApiService personApiService;

    public TMDBApiService(RestTemplateBuilder restTemplateBuilder,
            MovieApiService movieApiService, TvShowApiService tvShowApiService, PersonApiService personApiService) {

        this.movieApiService = movieApiService;
        this.tvShowApiService = tvShowApiService;
        this.personApiService = personApiService;
    }

    public Person fetchPerson(int personId) {
        return personApiService.fetchPerson(personId);
    }

    public Movie fetchMovie(int movieId) {
        return movieApiService.fetchMovie(movieId);
    }

    public TvShow fetchTvShow(int tvShowId) {
        return tvShowApiService.fetchTvShow(tvShowId);
    }

}