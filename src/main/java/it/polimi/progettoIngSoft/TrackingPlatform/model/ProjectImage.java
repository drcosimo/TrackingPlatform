package it.polimi.progettoIngSoft.TrackingPlatform.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProjectImage extends Snapshot{

    @ManyToOne
    @JoinColumn(name = "project")
    private Project project;

    public ProjectImage() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
