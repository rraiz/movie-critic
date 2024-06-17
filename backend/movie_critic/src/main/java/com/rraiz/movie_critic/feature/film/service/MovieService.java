package com.rraiz.movie_critic.feature.film.service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.feature.film.model.entity.Movie;
import com.rraiz.movie_critic.feature.film.model.identifier.FilmId;
import com.rraiz.movie_critic.feature.film.repository.MovieRepository;

import jakarta.transaction.Transactional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    private final TMDBApiService tmdbApiService;

    public MovieService(MovieRepository movieRepository, @Lazy TMDBApiService tmdbApiService) {
        this.movieRepository = movieRepository;
        this.tmdbApiService = tmdbApiService;
    }

    @Transactional
    public void addMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Transactional
    public Movie getMovieById(int movieId) {
        FilmId id = new FilmId(movieId, 0);
        return movieRepository.findById(id).orElse(null);
    }

    public Movie getMovieDetails(int movieId) throws Exception {
        Movie m = getMovieById(movieId);
        if (m == null || m.getLastUpdated() == null) {
            m = tmdbApiService.fetchMovieDetails(movieId);
            if (m != null)
                addMovie(m);
        }
        return m;
    }

    public List<Movie> getMoviesByName(String movieName) {
        return movieRepository.findByTitleContaining(movieName);
    }

}
