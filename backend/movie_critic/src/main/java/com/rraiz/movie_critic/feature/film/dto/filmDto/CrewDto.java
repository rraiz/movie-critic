package com.rraiz.movie_critic.feature.film.dto.filmDto;

import com.rraiz.movie_critic.feature.film.model.entity.Crew;

public class CrewDto {

    private Integer personId;
    private String department;
    private String job;
    private String personName;
    private String profilePath;

    public CrewDto() {
    }

    public CrewDto(Crew crew) {
        this.personId = crew.getId().getPersonId();
        this.department = crew.getId().getDepartment();
        this.job = crew.getId().getJob();
        this.personName = crew.getPerson().getName();
        this.profilePath = crew.getPerson().getProfilePath();
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
    
}
