package com.rraiz.movie_critic.service;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.model.Produced;
import com.rraiz.movie_critic.model.ProductionCompany;
import com.rraiz.movie_critic.repository.ProducedRepository;
import com.rraiz.movie_critic.repository.ProductionCompanyRepository;

@Service
public class ProductionCompanyService {

    private final ProductionCompanyRepository productionCompanyRepository;
    private final ProducedRepository producedRepository;

    public ProductionCompanyService(ProductionCompanyRepository productionCompanyRepository, ProducedRepository producedRepository) {
        this.productionCompanyRepository = productionCompanyRepository;
        this.producedRepository = producedRepository;
    }

    public void addProductionCompany(ProductionCompany productionCompany) {
        productionCompanyRepository.save(productionCompany);
    }

    public void addProduced(Produced produced) {
        producedRepository.save(produced);
    }
    
}
