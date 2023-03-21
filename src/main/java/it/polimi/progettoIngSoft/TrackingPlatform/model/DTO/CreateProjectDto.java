package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;


import java.time.Instant;
import java.util.List;

public class CreateProjectDto {

    private String token;
    private String name;
    private String description;
    private Instant beginDate;
    private Instant endDate;
    private List<String> partecipants;
    private List<String> creators;
    private List<String> admins;

    public CreateProjectDto(String token,String name, String description, Instant beginDate, Instant endDate) {
        this.token = token;
        this.name = name;
        this.description = description;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
