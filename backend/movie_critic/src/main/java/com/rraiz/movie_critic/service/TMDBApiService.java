package com.rraiz.movie_critic.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rraiz.movie_critic.model.*;

@Service
public class TMDBApiService {

    private static final String TMDB_API_BASE_URL = "https://api.themoviedb.org/3";
    private static final Logger logger = LoggerFactory.getLogger(TMDBApiService.class);

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final ObjectMapper objectMapper;
    private final MovieService movieService;
    private final CollectionService collectionService;

    public TMDBApiService(RestTemplateBuilder restTemplateBuilder, @Value("${TMDB_API_KEY}") String apiKey, MovieService movieService, CollectionService collectionService) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiKey = apiKey;
        this.objectMapper = new ObjectMapper();
        this.movieService = movieService;
        this.collectionService = collectionService;
    }

    private <T> T fetchFromApi(String url, Function<JsonNode, T> mappingFunction) {
        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(jsonResponse);
            return mappingFunction.apply(root);
        } catch (Exception e) {
            logger.error("Error fetching data from URL {}: {}", url, e.getMessage());
            return null;
        }
    }

    public Movie fetchMovieDetails(int movieId) {
        String url = String.format("%s/movie/%d?api_key=%s", TMDB_API_BASE_URL, movieId, apiKey);
        return fetchFromApi(url, this::mapApiResponseToMovie);
    }

    private Movie mapApiResponseToMovie(JsonNode root) {
        Movie movie = new Movie();

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
        Long budget = getValueAsLong(root.get("budget"));
        LocalDate releaseDate = getValueAsLocalDate(root.get("release_date"));
        Long revenue = getValueAsLong(root.get("revenue"));
        Integer runtime = getValueAsInt(root.get("runtime"));

        Collection collection = null;
        if (root.get("belongs_to_collection") != null) {
            JsonNode collNode = root.get("belongs_to_collection");
            Integer colId = getValueAsInt(collNode.get("id"));
            collection = collectionService.getCollectionById(colId);
            if (collection == null) {
                collection = new Collection();
                collection.setId(colId);
                collection.setName(getValueAsText(collNode.get("name")));
                collection.setPosterPath(getValueAsText(collNode.get("poster_path")));
                collection.setBackdropPath(getValueAsText(collNode.get("backdrop_path")));
            }
            Set<Movie> movieList = collection.getMovies();
            if(movieList == null)
                movieList = new HashSet<>();
            movieList.add(movie);
            collection.setMovies(movieList);
            collectionService.addCollection(collection);
        }

        Set<Crew> crew = Set.of(); // Map crew if available
        Set<Cast> cast = Set.of(); // Map cast if available
        Set<Produced> produced = Set.of(); // Map produced if available

        movie.setId(filmId);
        movie.setTitle(title);
        movie.setAdult(adult);
        movie.setHomepage(homepage);
        movie.setBackdropPath(backdropPath);
        movie.setPosterPath(posterPath);
        movie.setOriginalName(originalName);
        movie.setOriginalLanguage(originalLanguage);
        movie.setOverview(overview);
        movie.setPopularity(popularity);
        movie.setVoteCount(voteCount);
        movie.setVoteAverage(voteAverage);
        movie.setGenres(genres);
        movie.setProductionCountries(productionCountries);
        movie.setSpokenLanguages(spokenLanguages);
        movie.setOriginCountries(originCountries);
        movie.setCrew(crew);
        movie.setCast(cast);
        movie.setProduced(produced);
        movie.setTagline(tagline);
        movie.setBudget(budget);
        movie.setReleaseDate(releaseDate);
        movie.setRevenue(revenue);
        movie.setRuntime(runtime);
        movie.setCollection(collection);
        movie.setLastUpdated(LocalDate.now());

        movieService.addMovie(movie);

        return movie;
    }

    private String getValueAsText(JsonNode node) {
        return node != null && !node.isNull() ? node.asText() : null;
    }

    private Integer getValueAsInt(JsonNode node) {
        return node != null && !node.isNull() ? node.asInt() : null;
    }

    private Long getValueAsLong(JsonNode node) {
        return node != null && !node.isNull() ? node.asLong() : null;
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
