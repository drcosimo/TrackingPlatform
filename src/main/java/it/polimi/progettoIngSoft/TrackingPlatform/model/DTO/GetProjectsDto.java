package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;



public class GetProjectsDto {
    private String token;

    public GetProjectsDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public GetProjectsDto() {
    }
}
