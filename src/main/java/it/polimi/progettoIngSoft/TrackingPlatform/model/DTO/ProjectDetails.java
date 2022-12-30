package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;


import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Project;

import java.time.Instant;

public class ProjectDetails {

    private Long id;

    private String name;

    private String description;

    private Instant beginDate;

    private Instant endDate;

    public ProjectDetails(Long id, String name, String description, Instant beginDate, Instant endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public ProjectDetails(String name, String description, Instant beginDate, Instant endDate) {
        this.name = name;
        this.description = description;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public ProjectDetails(Project project){
        this.name = project.getName();
        this.description = project.getDescription();
        this.beginDate = project.getBeginDate();
        this.endDate = project.getEndDate();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Instant beginDate) {
        this.beginDate = beginDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }
}
