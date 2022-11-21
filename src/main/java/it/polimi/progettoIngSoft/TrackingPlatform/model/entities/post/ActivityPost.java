package it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.vehicle.Vehicle;

import javax.persistence.Entity;
import java.time.Instant;
import java.util.List;

@Entity
public class ActivityPost extends Activity {

    public ActivityPost() {
    }

    public ActivityPost (String name, String description, Instant beginDate, Instant endDate, List<Vehicle> vehicles, User creator) {
        setName(name);
        setDescription(description);
        setBeginDate(beginDate);
        setEndDate(endDate);
        setVehicles(vehicles);
        setCreators(List.of(creator));
        setAdmins(List.of(creator));
    }
}
