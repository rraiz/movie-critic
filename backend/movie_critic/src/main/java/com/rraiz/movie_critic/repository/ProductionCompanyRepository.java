package com.rraiz.movie_critic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rraiz.movie_critic.model.ProductionCompany;

public interface ProductionCompanyRepository extends JpaRepository<ProductionCompany, Integer>{

    
} 