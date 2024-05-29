package com.rraiz.movie_critic.service;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.model.FilmId;
import com.rraiz.movie_critic.model.Movie;
import com.rraiz.movie_critic.repository.MovieRepository;

import jakarta.transaction.Transactional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    private TMDBApiService tmdbApiService;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
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
        if (m == null) {
            m = tmdbApiService.fetchMovieDetails(movieId);
            addMovie(m);
        }
        return m;
    }

}
