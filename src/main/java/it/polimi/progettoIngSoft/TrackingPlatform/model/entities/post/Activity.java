package it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.vehicle.Vehicle;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public abstract class Activity extends Post{

    @ManyToMany
    @JoinTable(
            name = "activities_vehicles",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    private List<Vehicle> vehicles;

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
