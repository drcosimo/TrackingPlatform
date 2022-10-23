package it.polimi.progettoIngSoft.TrackingPlatform;

import it.polimi.progettoIngSoft.TrackingPlatform.model.Admin;
import it.polimi.progettoIngSoft.TrackingPlatform.model.GeneralGuest;
import it.polimi.progettoIngSoft.TrackingPlatform.model.Guest;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.GeneralGuestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

@SpringBootTest
public class GeneralGuestCreationTest {

    @Autowired
    private GeneralGuestRepository guestRepository;

    //the following two tests check the correct registration in db of different GeneralGuest instances
    @Test
    public void generateGuestTest(){
        Guest newGuest = new Guest();
        newGuest.setEmail("nicco.deca27@gmail.com");
        newGuest.setName("nicco");
        newGuest.setBirthDate(Instant.now().minusSeconds(999999L));
        newGuest.setPassword("ciao");
        newGuest.setSex("male");
        newGuest.setSurname("deca");
        newGuest.setUsername("niccodeca");
        GeneralGuest generalGuest = guestRepository.saveAndFlush(newGuest);
        System.out.println("guestId =" + generalGuest.getId());
        generalGuest = guestRepository.findById(generalGuest.getId()).get();
        Assertions.assertTrue(generalGuest instanceof Guest);
        Assertions.assertFalse(generalGuest instanceof Admin);
    }

    @Test
    public void generateAdminTest(){
        Admin newAdmin = new Admin();
        newAdmin.setEmail("nicco.admin@gmail.com");
        newAdmin.setName("nicco");
        newAdmin.setBirthDate(Instant.now().minusSeconds(999999L));
        newAdmin.setPassword("ciao");
        newAdmin.setSex("male");
        newAdmin.setSurname("deca");
        newAdmin.setUsername("niccoAdmin");
        GeneralGuest generalGuest = guestRepository.saveAndFlush(newAdmin);
        System.out.println("guestId =" + generalGuest.getId());
        generalGuest = guestRepository.findById(generalGuest.getId()).get();
        Assertions.assertFalse(generalGuest instanceof Guest);
        Assertions.assertTrue(generalGuest instanceof Admin);
    }
}
