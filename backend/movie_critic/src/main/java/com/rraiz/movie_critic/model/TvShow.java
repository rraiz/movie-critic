package com.rraiz.movie_critic.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Created> created;

    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Season> seasons;

    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TvShowNetwork> tvShowNetworks;


    // Default Constructor
    public TvShow() {
        super();
    }

    // Parameterized Constructor
    public TvShow(FilmId id, String title, boolean adult, String homepage, String backdrop_path, String poster_path, String original_name, String original_language, String overview, Double popularity, Integer vote_count, Double vote_average, List<String> genres, List<String> production_countries, List<String> spoken_languages, List<String> origin_countries, Set<Crew> crew, Set<Cast> cast, Set<Produced> produced, LocalDate firstAirDate, LocalDate lastAirDate, boolean inProduction, Integer numberOfEpisodes, Integer numberOfSeasons, Set<Created> created, Set<Season> seasons, Set<TvShowNetwork> tvShowNetworks) {
        super(id, title, adult, homepage, backdrop_path, poster_path, original_name, original_language, overview, popularity, vote_count, vote_average, genres, production_countries, spoken_languages, origin_countries, crew, cast, produced);
        this.firstAirDate = firstAirDate;
        this.lastAirDate = lastAirDate;
        this.inProduction = inProduction;
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOfSeasons;
        this.created = created;
        this.seasons = seasons;
        this.tvShowNetworks = tvShowNetworks;
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

    public boolean isInProduction() {
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

    public Set<Created> getCreated() {
        return created;
    }

    public void setCreated(Set<Created> created) {
        this.created = created;
    }

    public Set<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(Set<Season> seasons) {
        this.seasons = seasons;
    }

    public Set<TvShowNetwork> getTvShowNetworks() {
        return tvShowNetworks;
    }

    public void setTvShowNetworks(Set<TvShowNetwork> tvShowNetworks) {
        this.tvShowNetworks = tvShowNetworks;
    }
}
