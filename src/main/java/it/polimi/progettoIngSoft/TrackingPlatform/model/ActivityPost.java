package it.polimi.progettoIngSoft.TrackingPlatform.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ActivityPost extends Snapshot{

    @ManyToOne
    @JoinColumn(name = "activity")
    private Activity activity;

    public ActivityPost() {
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
