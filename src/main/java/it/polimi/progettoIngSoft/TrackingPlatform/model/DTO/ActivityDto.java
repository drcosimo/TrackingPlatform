package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Activity;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityDto {
    private Long id;

    private String name;

    private String description;

    private Instant beginDate;

    private Instant endDate;

    private String projectName;

    private Integer numberOfPosts;

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
            numberOfPosts = activity.getPostImages().size();
        }
        else numberOfPosts = 0;
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
        return numberOfPosts;
    }

    public void setNumberOfPosts(Integer numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }
}
