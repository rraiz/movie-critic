package com.rraiz.movie_critic.feature.content_management.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.feature.content_management.model.PopularFilms;
import com.rraiz.movie_critic.feature.content_management.repository.PopularFilmsRepository;
import com.rraiz.movie_critic.feature.film.model.entity.Film;
import com.rraiz.movie_critic.feature.film.model.entity.Movie;
import com.rraiz.movie_critic.feature.film.model.entity.TvShow;
import com.rraiz.movie_critic.util.TMDBApiService;

@Service
public class PopularFilmsService {

    private final PopularFilmsRepository popularFilmsRepository;
    private final TMDBApiService tmdbApiService;

    public PopularFilmsService(PopularFilmsRepository popularFilmsRepository, TMDBApiService tmdbApiService) {
        this.popularFilmsRepository = popularFilmsRepository;
        this.tmdbApiService = tmdbApiService;
    }

    @SuppressWarnings("unchecked")
    public <T extends Film> Set<T> getPopularFilms(int type) {
        Optional<PopularFilms> optionalPopularFilms = popularFilmsRepository.findById(1);

        PopularFilms popularFilms;
        if (optionalPopularFilms.isEmpty()) {
            popularFilms = new PopularFilms();
            popularFilms.setId(1);
            popularFilmsRepository.save(popularFilms);
        } else {
            popularFilms = optionalPopularFilms.get();
        }

        if (popularFilms.getLastUpdated() == null
                || popularFilms.getLastUpdated().isBefore(LocalDate.now().minusDays(7))) {
            updatePopularFilms(popularFilms);
        }

        if (type == 0) {
            return (Set<T>) popularFilms.getMovies();
        } else {
            return (Set<T>) popularFilms.getTvShows();
        }
    }

    @SuppressWarnings("unchecked")
    private void updatePopularFilms(PopularFilms popularFilms) {
        Set<Film> popularMovies = tmdbApiService.fetchPopularFilms(0);
        Set<Film> popularTvShows = tmdbApiService.fetchPopularFilms(1);
        popularFilms.setMovies((Set<Movie>) (Set<?>) popularMovies);
        popularFilms.setTvShows((Set<TvShow>) (Set<?>) popularTvShows);
        popularFilms.setLastUpdated(LocalDate.now());
        popularFilmsRepository.save(popularFilms);
    }

}
