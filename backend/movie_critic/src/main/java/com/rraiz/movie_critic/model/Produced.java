package com.rraiz.movie_critic.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "produced")
public class Produced {

    @EmbeddedId
    private ProducedId id;

    @ManyToOne
    @MapsId("companyId")
    @JoinColumn(name = "production_company_id")
    private ProductionCompany productionCompany;

    @ManyToOne
    @MapsId("filmId")
    @JoinColumn(name = "film_id")
    private Film film;

    public Produced() {
    }

    public Produced(ProducedId id, ProductionCompany productionCompany, Film film) {
        this.id = id;
        this.productionCompany = productionCompany;
        this.film = film;
    }

    public ProducedId getId() {
        return id;
    }

    public void setId(ProducedId id) {
        this.id = id;
    }

    public ProductionCompany getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(ProductionCompany productionCompany) {
        this.productionCompany = productionCompany;
    }
    
}
