package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

public class ChangeVisibilityDto {

    private String token;
    private boolean visibility;

    private Long idProject;

    public ChangeVisibilityDto(){

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public Long getIdProject() {
        return idProject;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }
}
