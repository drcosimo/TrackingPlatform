package it.polimi.progettoIngSoft.TrackingPlatform.model.entities.vehicle;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.image.Icon;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Activity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehicle")
    private Long id;

    @ManyToMany(mappedBy = "vehicles")
    private List<Activity> activities = null;

    @OneToOne
    @JoinColumn(name = "id_image")
    private Icon icon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
