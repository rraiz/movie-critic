package com.rraiz.movie_critic.dto;

import com.rraiz.movie_critic.model.Media;

import java.util.List;

/**
 * Data Transfer Object for the Media and Casts to display the cast members of a singular media in the API.
 */
public class MediaCastDTO {

    private Media media;
    private List<CastDTO> casts;

    public MediaCastDTO(Media media, List<CastDTO> casts) {
        this.media = media;
        this.casts = casts;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public List<CastDTO> getCasts() {
        return casts;
    }

    public void setCasts(List<CastDTO> casts) {
        this.casts = casts;
    }
}
