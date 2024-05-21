package com.rraiz.movie_critic.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TvShowNetworkId {

    @Column(name = "tv_show_id")
    private Integer tvShowId;

    @Column(name = "network_id")
    private Integer networkId;

    // Constructors
    public TvShowNetworkId() {
    }

    public TvShowNetworkId(Integer tvShowId, Integer networkId) {
        this.tvShowId = tvShowId;
        this.networkId = networkId;
    }

    // Getters and setters
    public Integer getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(Integer tvShowId) {
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
