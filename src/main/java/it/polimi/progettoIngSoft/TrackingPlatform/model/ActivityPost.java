package it.polimi.progettoIngSoft.TrackingPlatform.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ActivityPost extends Snapshot{

    @ManyToOne
    @JoinColumn(name = "activity")
    private Activity activity;

}
