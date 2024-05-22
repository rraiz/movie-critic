package com.rraiz.movie_critic.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rraiz.movie_critic.model.Movie;
import com.rraiz.movie_critic.model.TvShow;


@Service
public class TMDBApiService {

    private static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";

    private final RestTemplate restTemplate;
    private final String apiKey;

    public TMDBApiService(RestTemplateBuilder restTemplateBuilder, @Value("${TMDB_API_KEY}") String apiKey) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiKey = apiKey;
    }

    public TvShow fetchTvShowDetails(int tvShowId) {
        String url = String.format("%s/tv/%d?api_key=%s", TMDB_API_BASE_URL, tvShowId, apiKey);
        return restTemplate.getForObject(url, TvShow.class);
    }

    public Movie fetchMovieDetails(int movieId) {
        String url = String.format("%s/movie/%d?api_key=%s", TMDB_API_BASE_URL, movieId, apiKey);
        return restTemplate.getForObject(url, Movie.class);
    }
}

