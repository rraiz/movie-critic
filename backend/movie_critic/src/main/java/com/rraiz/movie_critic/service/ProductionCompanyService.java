package com.rraiz.movie_critic.service;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.model.Produced;
import com.rraiz.movie_critic.model.ProducedId;
import com.rraiz.movie_critic.model.ProductionCompany;
import com.rraiz.movie_critic.repository.ProducedRepository;
import com.rraiz.movie_critic.repository.ProductionCompanyRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductionCompanyService {

    private final ProductionCompanyRepository productionCompanyRepository;
    private final ProducedRepository producedRepository;

    public ProductionCompanyService(ProductionCompanyRepository productionCompanyRepository, ProducedRepository producedRepository) {
        this.productionCompanyRepository = productionCompanyRepository;
        this.producedRepository = producedRepository;
    }

    /* Production Company */
    @Transactional
    public void addProductionCompany(ProductionCompany productionCompany) {
        productionCompanyRepository.save(productionCompany);
    }

    @Transactional
    public ProductionCompany getProductionCompanyById(int productionCompanyId) {
        return productionCompanyRepository.findById(productionCompanyId).orElse(null);
    }


    /* Produced */
    @Transactional
    public void addProduced(Produced produced) {
        producedRepository.save(produced);
    }

    @Transactional
    public Produced getProducedById(ProducedId producedId) {
        return producedRepository.findById(producedId).orElse(null);
    }
    
}
