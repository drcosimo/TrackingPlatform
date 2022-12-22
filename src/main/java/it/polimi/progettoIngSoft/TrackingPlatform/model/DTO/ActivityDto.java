package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Activity;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.vehicle.Vehicle;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityDto {
    private Long id;

    private String name;

    private String description;

    private Instant beginDate;

    private Instant endDate;

    private String projectName;

    private Integer numberOfImages;

    private List<Long> vehiclesIds;

    private String error;

    public ActivityDto() {
    }

    public ActivityDto(Activity activity) {
        id = activity.getId();
        name = activity.getName();
        description = activity.getDescription();
        beginDate = activity.getBeginDate();
        endDate = activity.getEndDate();
        projectName = activity.getName();
        if(activity.getPostImages() != null){
            numberOfImages = activity.getPostImages().size();
        }
        else numberOfImages = 0;
        vehiclesIds = new ArrayList<>();
        for (Vehicle v : activity.getVehicles()) {
            vehiclesIds.add(v.getId());
        }
    }

    public ActivityDto(String error) {
        this.error = error;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getNumberOfPosts() {
        return numberOfImages;
    }

    public void setNumberOfPosts(Integer numberOfImages) {
        this.numberOfImages = numberOfImages;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
