package com.rraiz.movie_critic.feature.content_management.service;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.feature.content_management.model.PopularFilms;
import com.rraiz.movie_critic.feature.content_management.repository.PopularFilmsRepository;
import com.rraiz.movie_critic.feature.film.model.entity.Movie;
import com.rraiz.movie_critic.feature.film.model.entity.TvShow;

@Service
public class PopularFilmsService {

    private final PopularFilmsRepository popularFilmsRepository;

    public PopularFilmsService(PopularFilmsRepository popularFilmsRepository) {
        this.popularFilmsRepository = popularFilmsRepository;
    }

    public Set<Movie> getPopularMovies() {

        PopularFilms popularFilms = popularFilmsRepository.findById(1).get();
        if ( popularFilms.getLastUpdated() == null || popularFilms.getLastUpdated().isBefore( LocalDate.now().minusDays(7))){
            // fetch popular movies from TMDB API
            // update popularFilms with new movies
            // save popularFilms
        }

        return popularFilmsRepository.findById(1).get().getMovies();
    }

    public Set<TvShow> getPopularTvShows() {
        return popularFilmsRepository.findById(1).get().getTvShows();
    }

}
