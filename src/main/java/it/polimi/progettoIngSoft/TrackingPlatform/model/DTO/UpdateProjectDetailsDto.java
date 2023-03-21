package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

import javax.persistence.Column;
import java.time.Instant;

public class UpdateProjectDetailsDto {

    private String token;

    private Long idProject;

    private String name;

    private String description;

    private Instant beginDate;

    private Instant endDate;

    public UpdateProjectDetailsDto(String token,Long idProject, String name, String description, Instant beginDate, Instant endDate) {
        this.token = token;
        this.idProject = idProject;
        this.name = name;
        this.description = description;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public Long getIdProject() {
        return idProject;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
