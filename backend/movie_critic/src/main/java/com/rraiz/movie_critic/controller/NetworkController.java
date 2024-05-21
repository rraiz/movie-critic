package com.rraiz.movie_critic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rraiz.movie_critic.model.Network;
import com.rraiz.movie_critic.service.NetworkService;

@RestController
@RequestMapping(path = "/api/v1/networks")
public class NetworkController {

    private final NetworkService networkService;

    public NetworkController(NetworkService networkService) {
        this.networkService = networkService;
    }

    @GetMapping("/{networkId}")
    public ResponseEntity<Network> getNetworkById(@PathVariable("networkId") int networkId) {
        Network network = networkService.getNetworkById(networkId);
                
        if (network != null) {
            return new ResponseEntity<>(network, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
}
