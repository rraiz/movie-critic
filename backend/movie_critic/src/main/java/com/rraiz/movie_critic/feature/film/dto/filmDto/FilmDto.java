package com.rraiz.movie_critic.feature.film.dto.filmDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rraiz.movie_critic.feature.film.model.entity.Cast;
import com.rraiz.movie_critic.feature.film.model.entity.Crew;
import com.rraiz.movie_critic.feature.film.model.entity.Film;
import com.rraiz.movie_critic.feature.film.model.entity.ProductionCompany;

public class FilmDto {

    // Single values
    private Integer id;
    private Integer type;
    private String title; 
    private String homepage;
    private String backdropPath;
    private String posterPath;
    private String originalName;
    private String originalLanguage;
    private String overview;
    private Double popularity;
    private String tagline;
    private Integer voteCount;
    private Double voteAverage;

    // Multi values
    private List<String> genres;
    private List<String> productionCountries;
    private List<String> spokenLanguages;
    private List<String> originCountries;

    // References cast, crew, creator as a DTO object
    private Set<ProductionCompany> productionCompanies;
    private Set<CastDto> cast;
    private Set<CrewDto> crew;

    public FilmDto() {
    }

    public FilmDto(Film film)
    {
        this.id = film.getId().getFilmId();
        this.type = film.getId().getFilmType();
        this.title = film.getTitle();
        this.homepage = film.getHomepage();
        this.backdropPath = film.getBackdropPath();
        this.posterPath = film.getPosterPath();
        this.originalName = film.getOriginalName();
        this.originalLanguage = film.getOriginalLanguage();
        this.overview = film.getOverview();
        this.popularity = film.getPopularity();
        this.tagline = film.getTagline();
        this.voteCount = film.getVoteCount();
        this.voteAverage = film.getVoteAverage();

        this.genres = film.getGenres();
        this.productionCountries = film.getProductionCountries();
        this.spokenLanguages = film.getSpokenLanguages();
        this.originCountries = film.getOriginCountries();
        this.productionCompanies = film.getProduced();

        Set<Cast> filmCast = film.getCast();
        Set<CastDto> cast = null;
        if(filmCast != null)
        {
            cast = new HashSet<>();
            for(Cast c : filmCast)
            {
                cast.add(new CastDto(c));
            }
        }
        this.cast = cast;

        Set<Crew> filmCrew = film.getCrew();
        Set<CrewDto> crew = null;
        if(filmCrew != null)
        {
            crew = new HashSet<>();
            for(Crew c : filmCrew)
            {
                crew.add(new CrewDto(c));
            }
        }
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<String> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public List<String> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<String> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public List<String> getOriginCountries() {
        return originCountries;
    }

    public void setOriginCountries(List<String> originCountries) {
        this.originCountries = originCountries;
    }

    public Set<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(Set<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public Set<CastDto> getCast() {
        return cast;
    }

    public void setCast(Set<CastDto> cast) {
        this.cast = cast;
    }

    public Set<CrewDto> getCrew() {
        return crew;
    }

    public void setCrew(Set<CrewDto> crew) {
        this.crew = crew;
    }
}
