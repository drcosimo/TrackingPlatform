package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestActivityDto {
    private Long id;

    private String name;

    private String description;

    private Instant beginDate;

    private Instant endDate;

    private Long projectId;

    private List<Long> vehiclesIds;

    private String token;

    public RequestActivityDto() {
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getVehiclesIds() {
        return vehiclesIds;
    }

    public void setVehiclesIds(List<Long> vehiclesIds) {
        this.vehiclesIds = vehiclesIds;
    }
}
