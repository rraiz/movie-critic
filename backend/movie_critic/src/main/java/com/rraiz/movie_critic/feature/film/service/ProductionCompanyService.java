package com.rraiz.movie_critic.feature.film.service;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.feature.film.model.entity.ProductionCompany;
import com.rraiz.movie_critic.feature.film.repository.ProductionCompanyRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductionCompanyService {

    private final ProductionCompanyRepository productionCompanyRepository;

    public ProductionCompanyService(ProductionCompanyRepository productionCompanyRepository) {
        this.productionCompanyRepository = productionCompanyRepository;
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
    
}