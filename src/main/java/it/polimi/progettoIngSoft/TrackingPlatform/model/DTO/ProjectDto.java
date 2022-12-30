package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;


import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.image.PostImage;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Activity;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Project;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProjectDto {

    private Long id;

    private String name;

    private String description;

    private Instant beginDate;

    private Instant endDate;

    private boolean visible;

    private List<ActivityDto> activities = new ArrayList<ActivityDto>();

    private List<PostImage> images;

    private List<VisualizeUser> partecipants = new ArrayList<VisualizeUser>();

    private List<VisualizeUser> creators = new ArrayList<VisualizeUser>();

    private List<VisualizeUser> admins = new ArrayList<VisualizeUser>();

    public ProjectDto(Long id, String name, String description, Instant beginDate, Instant endDate, List<ActivityDto> activities, List<PostImage> images, List<VisualizeUser> partecipants, List<VisualizeUser> creators, List<VisualizeUser> admins) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.activities = activities;
        this.images = images;
        this.partecipants = partecipants;
        this.creators = creators;
        this.admins = admins;
    }

    public ProjectDto(Project project){
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.beginDate = project.getBeginDate();
        this.endDate = project.getEndDate();
        this.visible = project.isVisible();
        if (project.getActivities() != null) {
            for (Activity activity : project.getActivities()) {
                this.activities.add(new ActivityDto(activity));
            }
        }
        if(project.getPartecipants() != null) {
            for (User user : project.getPartecipants()) {
                this.partecipants.add(new VisualizeUser(user));
            }
        }
        if(project.getCreators() != null) {
            for (User user : project.getCreators()) {
                this.creators.add(new VisualizeUser(user));
            }
        }
        if(project.getAdmins() != null) {
            for (User user : project.getAdmins()) {
                this.admins.add(new VisualizeUser(user));
            }
        }
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

    public List<ActivityDto> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityDto> activities) {
        this.activities = activities;
    }

    public List<PostImage> getImages() {
        return images;
    }

    public void setImages(List<PostImage> images) {
        this.images = images;
    }

    public List<VisualizeUser> getPartecipants() {
        return partecipants;
    }

    public void setPartecipants(List<VisualizeUser> partecipants) {
        this.partecipants = partecipants;
    }

    public List<VisualizeUser> getCreators() {
        return creators;
    }

    public void setCreators(List<VisualizeUser> creators) {
        this.creators = creators;
    }

    public List<VisualizeUser> getAdmins() {
        return admins;
    }

    public void setAdmins(List<VisualizeUser> admins) {
        this.admins = admins;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
