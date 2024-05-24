package com.rraiz.movie_critic.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public class TvShowNetworkId implements Serializable {

    @Column(name = "tv_show_id")
    private FilmId tvShowId;

    @Column(name = "network_id")
    private Integer networkId;

    // Default constructor
    public TvShowNetworkId() {}

    // Parameterized constructor
    public TvShowNetworkId(FilmId tvShowId, Integer networkId) {
        this.tvShowId = tvShowId;
        this.networkId = networkId;
    }

    // Getters and setters
    public FilmId getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(FilmId tvShowId) {
        this.tvShowId = tvShowId;
    }

    public Integer getNetworkId() {
        return networkId;
    }

    public void setNetworkId(Integer networkId) {
        this.networkId = networkId;
    }

    // Equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TvShowNetworkId that = (TvShowNetworkId) o;
        return Objects.equals(tvShowId, that.tvShowId) &&
               Objects.equals(networkId, that.networkId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tvShowId, networkId);
    }
}
