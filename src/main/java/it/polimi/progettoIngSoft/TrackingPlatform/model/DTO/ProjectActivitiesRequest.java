package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

public class ProjectActivitiesRequest {
    private String token;
    private Long projectId;

    public ProjectActivitiesRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
