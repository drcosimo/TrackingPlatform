package it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.vehicle.Vehicle;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;
import java.util.List;

@Entity
public class ActivityProject extends Activity{

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User creator;

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public ActivityProject() {
    }

    public ActivityProject (String name, String description, Instant beginDate, Instant endDate, List<Vehicle> vehicles, User creator) {
        setName(name);
        setDescription(description);
        setBeginDate(beginDate);
        setEndDate(endDate);
        setVehicles(vehicles);
        //initial setting of creator's permissions
        setCreators(List.of(creator));
        setAdmins(List.of(creator));
    }
}
