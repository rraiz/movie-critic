package com.rraiz.movie_critic.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    private final ProductionCompanyService productionCompanyService;

    public TMDBApiService(RestTemplateBuilder restTemplateBuilder, @Value("${TMDB_API_KEY}") String apiKey,
            MovieService movieService, CollectionService collectionService,
            ProductionCompanyService productionCompanyService) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiKey = apiKey;
        this.objectMapper = new ObjectMapper();
        this.movieService = movieService;
        this.collectionService = collectionService;
        this.productionCompanyService = productionCompanyService;
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
        movie.setId(filmId);
        movieService.addMovie(movie);

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

        List<String> genres = root.get("genres").findValues("name").stream().map(JsonNode::asText)
                .collect(Collectors.toList());
        List<String> productionCountries = root.get("production_countries").findValues("name").stream()
                .map(JsonNode::asText).collect(Collectors.toList());
        List<String> spokenLanguages = root.get("spoken_languages").findValues("english_name").stream()
                .map(JsonNode::asText).collect(Collectors.toList());
        List<String> originCountries = StreamSupport.stream(root.get("origin_country").spliterator(), false)
                .map(JsonNode::asText).collect(Collectors.toList());

        String tagline = getValueAsText(root.get("tagline"));
        Long budget = getValueAsLong(root.get("budget"));
        LocalDate releaseDate = getValueAsLocalDate(root.get("release_date"));
        Long revenue = getValueAsLong(root.get("revenue"));
        Integer runtime = getValueAsInt(root.get("runtime"));

        // Map collection if available
        Collection collection = null;
        if (!root.get("belongs_to_collection").isNull()) {
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
            if (movieList == null)
                movieList = new HashSet<>();
            movieList.add(movie);
            collection.setMovies(movieList);
            collectionService.addCollection(collection);
        }

        Set<Crew> crew = Set.of(); // Map crew if available
        Set<Cast> cast = Set.of(); // Map cast if available

        // Map production companies if available
        Set<ProductionCompany> produced_set = null;
        if (!root.get("production_companies").isNull()) {

            produced_set = new HashSet<>(); // Creates a set of produced movies

            // Fore each production company
            for (JsonNode company : root.get("production_companies")) {
                // Extracts the id of the company
                int companyId = company.get("id").asInt();

                // Tries to find the company in the database
                ProductionCompany productionCompany = productionCompanyService.getProductionCompanyById(companyId);
                if (productionCompany == null) { // if not found then
                    productionCompany = new ProductionCompany(); // creates a new one
                    productionCompany.setId(companyId); // Sets id
                    productionCompany.setName(company.get("name").asText()); // name
                    productionCompany.setLogoPath(company.get("logo_path").asText()); // logo path
                    productionCompany.setOriginCountry(company.get("origin_country").asText()); // origin country
                    productionCompanyService.addProductionCompany(productionCompany); // Saves the company to db
                }

                Set<Film> producedFilm =  productionCompany.getProduced(); // Gets the set of produced movies
                if (producedFilm == null) // If the set is null
                    producedFilm = new HashSet<>(); // Creates a new set
                producedFilm.add(movie); // Adds the movie to the set
                productionCompany.setProduced(producedFilm); // Sets the set to the production company

                productionCompanyService.addProductionCompany(productionCompany); // Saves the production company to db
                produced_set.add(productionCompany); // Adds the production company to the set
            }
        }

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
        movie.setProduced(produced_set);
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
