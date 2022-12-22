package it.polimi.progettoIngSoft.TrackingPlatform.repository;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
