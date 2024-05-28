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

        String title = root.get("title").asText();
        boolean adult = root.get("adult").asBoolean();
        String homepage = root.has("homepage") ? root.get("homepage").asText() : null;
        String backdropPath = root.has("backdrop_path") ? root.get("backdrop_path").asText() : null;
        String posterPath = root.has("poster_path") ? root.get("poster_path").asText() : null;
        String originalName = root.has("original_title") ? root.get("original_title").asText() : null;
        String originalLanguage = root.has("original_language") ? root.get("original_language").asText() : null;
        String overview = root.has("overview") ? root.get("overview").asText() : null;
        Double popularity = root.has("popularity") ? root.get("popularity").asDouble() : null;
        Integer voteCount = root.has("vote_count") ? root.get("vote_count").asInt() : null;
        Double voteAverage = root.has("vote_average") ? root.get("vote_average").asDouble() : null;

        List<String> genres = root.get("genres").findValues("name").stream().map(JsonNode::asText).collect(Collectors.toList());
        List<String> productionCountries = root.get("production_countries").findValues("name").stream().map(JsonNode::asText).collect(Collectors.toList());
        List<String> spokenLanguages = root.get("spoken_languages").findValues("english_name").stream().map(JsonNode::asText).collect(Collectors.toList());
        List<String> originCountries = root.get("origin_country").findValues("name").stream().map(JsonNode::asText).collect(Collectors.toList());

        String tagline = root.has("tagline") ? root.get("tagline").asText() : null;
        Integer budget = root.has("budget") ? root.get("budget").asInt() : null;
        LocalDate releaseDate = root.has("release_date") ? LocalDate.parse(root.get("release_date").asText(), DateTimeFormatter.ISO_DATE) : null;
        Integer revenue = root.has("revenue") ? root.get("revenue").asInt() : null;
        Integer runtime = root.has("runtime") ? root.get("runtime").asInt() : null;

        Collection collection = null;
        if (root.has("belongs_to_collection") && !root.get("belongs_to_collection").isNull()) {
            // implement mapping of collection
        }

        Set<Crew> crew = Set.of(); // Map crew if available
        Set<Cast> cast = Set.of(); // Map cast if available
        Set<Produced> produced = Set.of(); // Map produced if available

        return new Movie(filmId, title, adult, homepage, backdropPath, posterPath, originalName, originalLanguage, overview, popularity, voteCount, voteAverage, genres, productionCountries, spokenLanguages, originCountries, crew, cast, tagline, budget, releaseDate, revenue, runtime, collection, produced, null);
    }
    
}

