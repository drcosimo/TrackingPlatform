package it.polimi.progettoIngSoft.TrackingPlatform.repository;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.ActivationElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivationElementRepository extends JpaRepository<ActivationElement, Long> {
    public ActivationElement findFirstByActivationTokenAndAlreadyUsed(String activationToken, Boolean isUsable);
}
