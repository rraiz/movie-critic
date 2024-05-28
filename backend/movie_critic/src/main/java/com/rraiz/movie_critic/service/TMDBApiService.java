package com.rraiz.movie_critic.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rraiz.movie_critic.model.Cast;
import com.rraiz.movie_critic.model.Collection;
import com.rraiz.movie_critic.model.Crew;
import com.rraiz.movie_critic.model.FilmId;
import com.rraiz.movie_critic.model.Movie;
import com.rraiz.movie_critic.model.Produced;
import com.rraiz.movie_critic.model.TvShow;


@Service
public class TMDBApiService {

    private static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final ObjectMapper objectMapper;


    public TMDBApiService(RestTemplateBuilder restTemplateBuilder, @Value("${TMDB_API_KEY}") String apiKey) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiKey = apiKey;
        this.objectMapper = new ObjectMapper();
    }

    public TvShow fetchTvShowDetails(int tvShowId) {
        String url = String.format("%s/tv/%d?api_key=%s", TMDB_API_BASE_URL, tvShowId, apiKey);
        return restTemplate.getForObject(url, TvShow.class);
    }

    public Movie fetchMovieDetails(int movieId) throws Exception {
        String url = String.format("%s/movie/%d?api_key=%s", TMDB_API_BASE_URL, movieId, apiKey);
        String jsonResponse = restTemplate.getForObject(url, String.class);
        return mapApiResponseToMovie(jsonResponse);
    }

    private Movie mapApiResponseToMovie(String jsonResponse) throws Exception {
        JsonNode root = objectMapper.readTree(jsonResponse);
    
        FilmId filmId = new FilmId(root.get("id").asInt(), 0);
    
        String title = getValueAsText(root.get("title"));
        Boolean adult = getValueAsBoolean(root.get("adult"));
        String homepage = getValueAsText(root.get("homepage"));
        String backdropPath = getValueAsText(root.get("backdrop_path"));
        String posterPath = getValueAsText(root.get("poster_path"));
        String originalName = getValueAsText(root.get("original_title"));
        String originalLanguage = getValueAsText(root.get("original_language"));
        String overview = getValueAsText(root.get("overview"));
        Double popularity = getValueAsDouble(root.get("popularity"));
        Integer voteCount = getValueAsInt(root.get("vote_count"));
        Double voteAverage = getValueAsDouble(root.get("vote_average"));
    
        List<String> genres = root.get("genres").findValues("name").stream().map(JsonNode::asText).collect(Collectors.toList());
        List<String> productionCountries = root.get("production_countries").findValues("name").stream().map(JsonNode::asText).collect(Collectors.toList());
        List<String> spokenLanguages = root.get("spoken_languages").findValues("english_name").stream().map(JsonNode::asText).collect(Collectors.toList());
        List<String> originCountries = root.get("origin_country").findValues("name").stream().map(JsonNode::asText).collect(Collectors.toList());
    
        String tagline = getValueAsText(root.get("tagline"));
        Integer budget = getValueAsInt(root.get("budget"));
        LocalDate releaseDate = getValueAsLocalDate(root.get("release_date"));
        Integer revenue = getValueAsInt(root.get("revenue"));
        Integer runtime = getValueAsInt(root.get("runtime"));
    
        Collection collection = null;
        if (root.get("belongs_to_collection") != null && !root.get("belongs_to_collection").isNull()) {
            // Implement mapping of collection
        }
    
        Set<Crew> crew = Set.of(); // Map crew if available
        Set<Cast> cast = Set.of(); // Map cast if available
        Set<Produced> produced = Set.of(); // Map produced if available
    
        return new Movie(filmId, title, adult, homepage, backdropPath, posterPath, originalName, originalLanguage, overview, popularity, voteCount, voteAverage, genres, productionCountries, spokenLanguages, originCountries, crew, cast, tagline, budget, releaseDate, revenue, runtime, collection, produced, null);
    }
    
    private String getValueAsText(JsonNode node) {
        return node != null && !node.isNull() ? node.asText() : null;
    }
    
    private Integer getValueAsInt(JsonNode node) {
        return node != null && !node.isNull() ? node.asInt() : null;
    }
    
    private Double getValueAsDouble(JsonNode node) {
        return node != null && !node.isNull() ? node.asDouble() : null;
    }
    
    private Boolean getValueAsBoolean(JsonNode node) {
        return node != null && !node.isNull() ? node.asBoolean() : null;
    }
    
    private LocalDate getValueAsLocalDate(JsonNode node) {
        return node != null && !node.isNull() ? LocalDate.parse(node.asText(), DateTimeFormatter.ISO_DATE) : null;
    }
    
    
}

