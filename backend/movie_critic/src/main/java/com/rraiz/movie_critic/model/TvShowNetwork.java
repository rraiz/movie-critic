package com.rraiz.movie_critic.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "tv_show_network")
public class TvShowNetwork {

    @EmbeddedId
    private TvShowNetworkId id;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "film_id", insertable = false, updatable = false),
        @JoinColumn(name = "film_type", insertable = false, updatable = false)
    })
    private TvShow tvShow;

    @ManyToOne
    @MapsId("networkId")
    @JoinColumn(name = "network_id")
    private Network network;

    public TvShowNetwork() {}

    public TvShowNetwork(TvShowNetworkId id, TvShow tvShow, Network network) {
        this.id = id;
        this.tvShow = tvShow;
        this.network = network;
    }

    // Getters and setters
    public TvShowNetworkId getId() {
        return id;
    }

    public void setId(TvShowNetworkId id) {
        this.id = id;
    }

    public TvShow getTvShow() {
        return tvShow;
    }

    public void setTvShow(TvShow tvShow) {
        this.tvShow = tvShow;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }
}
