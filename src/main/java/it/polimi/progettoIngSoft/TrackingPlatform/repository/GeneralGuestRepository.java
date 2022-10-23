package it.polimi.progettoIngSoft.TrackingPlatform.repository;

import it.polimi.progettoIngSoft.TrackingPlatform.model.GeneralGuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralGuestRepository extends JpaRepository<GeneralGuest, Long> {

    public GeneralGuest getByEmailAndPassword(String email, String password);
}
