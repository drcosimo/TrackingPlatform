package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

public class DeleteProjectDto {

    private String token;
    private Long idProject;

    public DeleteProjectDto(String token, Long idProject) {
        this.token = token;
        this.idProject = idProject;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getIdProject() {
        return idProject;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }
}
