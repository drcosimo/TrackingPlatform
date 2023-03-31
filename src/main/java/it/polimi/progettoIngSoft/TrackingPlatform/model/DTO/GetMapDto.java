package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

public class GetMapDto {

    private String token;
    private Long mapId;

    public GetMapDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getMapId() {
        return mapId;
    }

    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }
}
