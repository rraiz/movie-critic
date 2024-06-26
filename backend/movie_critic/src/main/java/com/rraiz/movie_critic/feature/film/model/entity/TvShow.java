package com.rraiz.movie_critic.feature.film.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rraiz.movie_critic.feature.film.model.identifier.FilmId;

@Entity
@Table(name = "tv_show")
public class TvShow extends Film {

    @Column(nullable = true, name = "first_air_date")
    private LocalDate firstAirDate;

    @Column(nullable = true, name = "last_air_date")
    private LocalDate lastAirDate;

    @Column(nullable = true)
    private boolean inProduction;

    @Column(nullable = true, name = "number_of_episodes")
    private Integer numberOfEpisodes;

    @Column(nullable = true, name = "number_of_seasons")
    private Integer numberOfSeasons;

    @ManyToMany
    @JoinTable(
        name = "tv_show_creators",
        joinColumns = {
            @JoinColumn(name = "film_id"),
            @JoinColumn(name = "film_type")
        },
        inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    @JsonManagedReference
    private Set<Person> creators;

    @ManyToMany
    @JoinTable(
        name = "tv_show_networks",
        joinColumns = {
            @JoinColumn(name = "film_id"),
            @JoinColumn(name = "film_type")
        },
        inverseJoinColumns = @JoinColumn(name = "network_id")
    )
    @JsonManagedReference
    private Set<Network> networks;


    // Default Constructor
    public TvShow() {
        super();
    }

    // Parameterized Constructor
    public TvShow(FilmId id, String title, boolean adult, String homepage, String backdrop_path, String poster_path, String original_name, String original_language, String overview, Double popularity, String tagline, Integer vote_count, Double vote_average, List<String> genres, List<String> production_countries, List<String> spoken_languages, List<String> origin_countries, Set<Crew> crew, Set<Cast> cast, Set<ProductionCompany> productionCompanies, LocalDate firstAirDate, LocalDate lastAirDate, boolean inProduction, Integer numberOfEpisodes, Integer numberOfSeasons, Set<Person> creators, Set<Network> networks, LocalDate lastUpdated) {
        super(id, title, adult, homepage, backdrop_path, poster_path, original_name, original_language, overview, popularity, tagline, vote_count, vote_average, genres, production_countries, spoken_languages, origin_countries, crew, cast, productionCompanies, lastUpdated);
        this.firstAirDate = firstAirDate;
        this.lastAirDate = lastAirDate;
        this.inProduction = inProduction;
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOfSeasons;
        this.creators = creators;
        this.networks = networks;
    }

    // Getters and Setters
    public LocalDate getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(LocalDate firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public LocalDate getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(LocalDate lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public boolean getInProduction() {
        return inProduction;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    public Integer getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(Integer numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(Integer numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public Set<Person> getCreators() {
        return creators;
    }

    public void setCreators(Set<Person> creators) {
        this.creators = creators;
    }

    public Set<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(Set<Network> networks) {
        this.networks = networks;
    }
}
