package com.rraiz.movie_critic.feature.content_management.api_external;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.rraiz.movie_critic.feature.film.api_external.FilmApiService;
import com.rraiz.movie_critic.feature.film.model.entity.Film;
import com.rraiz.movie_critic.feature.film.model.entity.Movie;
import com.rraiz.movie_critic.feature.film.model.entity.TvShow;
import com.rraiz.movie_critic.feature.film.model.identifier.FilmId;
import com.rraiz.movie_critic.util.ApiUtil;

@Service
public class PopularFilmsApiService {

    private final ApiUtil apiUtil;
    private final FilmApiService filmApiService;

    public PopularFilmsApiService(ApiUtil apiUtil, FilmApiService filmApiService) {
        this.apiUtil = apiUtil;
        this.filmApiService = filmApiService;
    }

    public Set<Film> fetchPopularFilms(int type) {
        return fetchFilmCredits(type);
    }

    public Set<Film> fetchFilmCredits(int filmType) {
        String endpoint = "/%s/popular".formatted(filmType == 0 ? "movie" : "tv");
        if (filmType == 0) {
            return apiUtil.fetchFromApi(endpoint, this::mapApiResponseToPopularMovies);
        } else {
            return apiUtil.fetchFromApi(endpoint, this::mapApiResponseToPopularTvShows);
        }
    }

    private Set<Film> mapApiResponseToPopularMovies(JsonNode root) {
        return mapApiResponseToPopularFilms(root, 0);
    }

    private Set<Film> mapApiResponseToPopularTvShows(JsonNode root) {
        return mapApiResponseToPopularFilms(root, 1);
    }

    private Set<Film> mapApiResponseToPopularFilms(JsonNode root, int filmType) {

        Set<Film> films = new HashSet<Film>();
        for (JsonNode filmNode : root.get("results")) {

            int filmId = filmNode.get("id").asInt();
            Film film = filmApiService.getFilmById(filmId, filmType);
            if (film == null) {
                film = filmApiService.createNewFilm(filmType);
            }
            if (film instanceof Movie) {
                ((Movie) film).setReleaseDate(apiUtil.getValueAsLocalDate(filmNode.get("release_date")));
            }
            if (film instanceof TvShow) {
                ((TvShow) film).setFirstAirDate(apiUtil.getValueAsLocalDate(filmNode.get("first_air_date")));
            }
            film.setId(new FilmId(filmId, filmType));
            filmApiService.mapCommonFilmAttributes(film, filmNode);
            filmApiService.addFilmToDatabase(film, filmType);
            films.add(film);
        }

        return films;
    }

}
