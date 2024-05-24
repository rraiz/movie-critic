package com.rraiz.movie_critic.service;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.model.Network;
import com.rraiz.movie_critic.model.TvShowNetwork;
import com.rraiz.movie_critic.repository.NetworkRepository;
import com.rraiz.movie_critic.repository.TvShowNetworkRepository;

@Service
public class NetworkService {
    
    private final NetworkRepository networkRepository;
    private final TvShowNetworkRepository tvShowNetworkRepository;

    public NetworkService(NetworkRepository networkRepository, TvShowNetworkRepository tvShowNetworkRepository) {
        this.networkRepository = networkRepository;
        this.tvShowNetworkRepository = tvShowNetworkRepository;
    }

    public void addNetwork(Network network) {
        networkRepository.save(network);
    }

    public void addTvShowNetwork(TvShowNetwork tvShowNetwork) {
        tvShowNetworkRepository.save(tvShowNetwork);
    }

    public Network getNetworkById(int networkId) {
        return networkRepository.findById(networkId).orElse(null);
    }
}
