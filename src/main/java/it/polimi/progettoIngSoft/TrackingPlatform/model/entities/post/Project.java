package it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Project extends Post {

    @OneToMany
    @JoinColumn(name = "id_project")
    private List<ActivityProject> activities;

    public Project() {
    }

    public List<ActivityProject> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityProject> activities) {
        this.activities = activities;
    }
}
