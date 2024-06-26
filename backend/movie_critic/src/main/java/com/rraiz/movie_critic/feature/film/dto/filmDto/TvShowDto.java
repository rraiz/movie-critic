package com.rraiz.movie_critic.feature.film.dto.filmDto;

import java.time.LocalDate;
import java.util.HashSet;

import java.util.Set;


import com.rraiz.movie_critic.feature.film.model.entity.Network;
import com.rraiz.movie_critic.feature.film.model.entity.Person;
import com.rraiz.movie_critic.feature.film.model.entity.TvShow;

public class TvShowDto extends FilmDto {
    

    private LocalDate firstAirDate;
    private LocalDate lastAirDate;
    private Boolean inProduction;
    private Integer numberOfEpisodes;
    private Integer numberOfSeasons;

    // Referenced objects as a set
    private Set<PersonDto> creators;
    private Set<Network> networks;

    private LocalDate lastUpdated;

    public TvShowDto() {
    }

    public TvShowDto(TvShow tv)
    {
        super(tv);
        this.inProduction = tv.getInProduction();
        this.numberOfEpisodes = tv.getNumberOfEpisodes();
        this.numberOfSeasons = tv.getNumberOfSeasons();
        this.firstAirDate = tv.getFirstAirDate();
        this.lastAirDate = tv.getLastAirDate();

        Set<Person> tvCreators = tv.getCreators();
        Set<PersonDto> creators = null;
        if(tvCreators != null)
        {
            creators = new HashSet<>();
            for(Person p : tvCreators)
            {
                creators.add(new PersonDto(p));
            }
        }
        this.creators = creators;
        this.networks = tv.getNetworks();
        this.lastUpdated = tv.getLastUpdated();
    }

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

    public Boolean getInProduction() {
        return inProduction;
    }

    public void setInProduction(Boolean inProduction) {
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

    public Set<PersonDto> getCreators() {
        return creators;
    }

    public void setCreators(Set<PersonDto> creators) {
        this.creators = creators;
    }

    public Set<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(Set<Network> networks) {
        this.networks = networks;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
