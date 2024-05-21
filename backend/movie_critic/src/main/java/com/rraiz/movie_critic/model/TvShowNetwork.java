package com.rraiz.movie_critic.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "tv_show_network")
public class TvShowNetwork {

    @EmbeddedId
    private TvShowNetworkId id;

    @ManyToOne
    @MapsId("tvShowId")
    @JoinColumn(name = "tv_show_id")
    private TvShow tvShow;

    @ManyToOne
    @MapsId("networkId")
    @JoinColumn(name = "network_id")
    private Network network;

    public TvShowNetwork() {
    }

    public TvShowNetwork(TvShowNetworkId id, TvShow tvShow, Network network) {
        this.id = id;
        this.tvShow = tvShow;
        this.network = network;
    }

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
