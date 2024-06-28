package com.rraiz.movie_critic.util;

import java.util.Set;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.feature.content_management.api_external.PopularFilmsApiService;
import com.rraiz.movie_critic.feature.film.api_external.MovieApiService;
import com.rraiz.movie_critic.feature.film.api_external.PersonApiService;
import com.rraiz.movie_critic.feature.film.api_external.TvShowApiService;
import com.rraiz.movie_critic.feature.film.model.entity.Film;
import com.rraiz.movie_critic.feature.film.model.entity.Movie;
import com.rraiz.movie_critic.feature.film.model.entity.Person;
import com.rraiz.movie_critic.feature.film.model.entity.TvShow;

@Service
public class TMDBApiService {

    private final MovieApiService movieApiService;
    private final TvShowApiService tvShowApiService;
    private final PersonApiService personApiService;
    private final PopularFilmsApiService popularFilmsApiService;

    public TMDBApiService(RestTemplateBuilder restTemplateBuilder,
            MovieApiService movieApiService, TvShowApiService tvShowApiService, PersonApiService personApiService, PopularFilmsApiService popularFilmsApiService) {

        this.movieApiService = movieApiService;
        this.tvShowApiService = tvShowApiService;
        this.personApiService = personApiService;
        this.popularFilmsApiService = popularFilmsApiService;
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

    public Set<Film> fetchPopularFilms(int type) {
        return popularFilmsApiService.fetchPopularFilms(type);
    }

}