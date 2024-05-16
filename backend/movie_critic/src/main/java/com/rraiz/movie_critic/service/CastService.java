package com.rraiz.movie_critic.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rraiz.movie_critic.dto.CastDTO;
import com.rraiz.movie_critic.model.Cast;
import com.rraiz.movie_critic.model.CastId;
import com.rraiz.movie_critic.repository.CastRepository;

import jakarta.transaction.Transactional;

@Service
public class CastService {

    private final CastRepository castRepository;

    public CastService(CastRepository castRepository) {
        this.castRepository = castRepository;
    }

    @Transactional
    public void addCast(Cast cast) {
        castRepository.save(cast);
    }

    @Transactional
    public Cast getCastById(CastId id) {
        return castRepository.findById(id).orElse(null);
    }

    @Transactional
    public Cast addForeignKey(CastId castId)
    {
        Cast cast = getCastById(castId);
        if (cast == null) {
            cast = new Cast();
            cast.setId(castId);
            addCast(cast);
        }
        return cast; 
    }

    /**
     * Retrieves all the casts of a media by its tconst.
     * The purpose of this method is to return a singular media entity
     * with a list of its associated casts to reduce redundancy.
     *
     * @param tconst the unique identifier of the media
     * @return a list of CastDTO objects representing the cast members of the media
     */
    public List<CastDTO> getCastsByMediaId(int tconst) {
        List<Cast> castList = castRepository.findByIdTconst(tconst);
        return castList.stream()
                .map(cast -> new CastDTO(cast.getId(), cast.getPerson(), cast.getCategory(), cast.getJob(), cast.getCharacters()))
                .collect(Collectors.toList());
    }
    
}
