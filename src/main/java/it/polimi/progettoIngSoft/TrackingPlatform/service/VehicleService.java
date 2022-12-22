package it.polimi.progettoIngSoft.TrackingPlatform.service;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.vehicle.Vehicle;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public boolean checkVehiclesExistence(List<Long> vehiclesIds) {
        try {
            for (Long id : vehiclesIds) {
                vehicleRepository.findById(id).get();
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public List<Vehicle> getAllByIds(List<Long> vehiclesIds) {
        if(vehiclesIds == null || vehiclesIds.isEmpty()) {
            return null;
        }
        else {
            return vehicleRepository.findAllById(vehiclesIds);
        }
    }
}
