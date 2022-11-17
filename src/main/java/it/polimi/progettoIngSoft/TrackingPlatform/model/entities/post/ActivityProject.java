package it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post;

import javax.persistence.Entity;
import java.time.Instant;

@Entity
public class ActivityProject extends Activity{

    public ActivityProject() {
    }

    public ActivityProject (String name, String description, Instant beginDate, Instant endDate) {
        setName(name);
        setDescription(description);
        setBeginDate(beginDate);
        setEndDate(endDate);
    }
}
