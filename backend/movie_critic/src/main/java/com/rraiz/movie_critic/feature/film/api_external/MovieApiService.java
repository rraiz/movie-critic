package com.rraiz.movie_critic.feature.film.api_external;


import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.rraiz.movie_critic.feature.film.model.entity.Collection;
import com.rraiz.movie_critic.feature.film.model.entity.Movie;
import com.rraiz.movie_critic.feature.film.model.identifier.FilmId;
import com.rraiz.movie_critic.feature.film.service.CollectionService;
import com.rraiz.movie_critic.feature.film.service.MovieService;
import com.rraiz.movie_critic.util.ApiUtil;

@Service
public class MovieApiService {

    private final ApiUtil apiUtil;
    private final FilmApiService filmApiService;
    private final MovieService movieService;
    private final CollectionService collectionService;

    public MovieApiService(ApiUtil apiUtil, FilmApiService filmApiService, MovieService movieService, CollectionService collectionService) {
        this.apiUtil = apiUtil;
        this.filmApiService = filmApiService;
        this.movieService = movieService;
        this.collectionService = collectionService;
    }

    public Movie fetchMovie(int movieId) {
        Movie movie = fetchMovieDetails(movieId);
        return movie;
    }

    public Movie fetchMovieDetails(int movieId) {
        String endpoint = "/movie/%d".formatted(movieId);
        return apiUtil.fetchFromApi(endpoint, this::mapApiResponseToMovie);
    }

    /**
     * Maps the JSON response from the API to a Movie object
     * 
     * @param root
     * @return Movie object
     */
    private Movie mapApiResponseToMovie(JsonNode root) {
        Movie movie = new Movie();

        FilmId filmId = new FilmId(root.get("id").asInt(), 0);
        movie.setId(filmId);
        movieService.addMovie(movie);
        filmApiService.mapFilm(root, movie);

        // Map collection if available
        Collection collection = mapMovieCollection(root, movie);

        movie.setBudget(apiUtil.getValueAsLong(root.get("budget")));
        movie.setReleaseDate(apiUtil.getValueAsLocalDate(root.get("release_date")));
        movie.setRevenue(apiUtil.getValueAsLong(root.get("revenue")));
        movie.setRuntime(apiUtil.getValueAsInt(root.get("runtime")));
        movie.setCollection(collection);
        movieService.addMovie(movie);

        return movie;
    }

    private Collection mapMovieCollection(JsonNode root, Movie movie) {
        if(root.get("belongs_to_collection").isNull()){
            return null;
        }

        Collection collection = new Collection();

        JsonNode collNode = root.get("belongs_to_collection");
        Integer colId = apiUtil.getValueAsInt(collNode.get("id"));
        collection = collectionService.getCollectionById(colId);
        if (collection == null) {
            collection = new Collection();
        }
        collection.setId(colId);
        collection.setName(apiUtil.getValueAsText(collNode.get("name")));
        collection.setPosterPath(apiUtil.getValueAsText(collNode.get("poster_path")));
        collection.setBackdropPath(apiUtil.getValueAsText(collNode.get("backdrop_path")));

        Set<Movie> movieList = collection.getMovies();
        if (movieList == null)
            movieList = new HashSet<>();
        movieList.add(movie);
        collection.setMovies(movieList);
        collectionService.addCollection(collection);

        return collection;
    }
    
}
