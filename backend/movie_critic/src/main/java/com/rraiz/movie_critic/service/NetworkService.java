package com.rraiz.movie_critic.service;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.model.Network;
import com.rraiz.movie_critic.repository.NetworkRepository;

@Service
public class NetworkService {
    
    private final NetworkRepository networkRepository;

    public NetworkService(NetworkRepository networkRepository) {
        this.networkRepository = networkRepository;
    }

    public void addNetwork(Network network) {
        networkRepository.save(network);
    }

    public Network getNetworkById(int networkId) {
        return networkRepository.findById(networkId).orElse(null);
    }
}
