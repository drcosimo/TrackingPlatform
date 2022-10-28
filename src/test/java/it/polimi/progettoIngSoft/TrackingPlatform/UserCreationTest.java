package it.polimi.progettoIngSoft.TrackingPlatform;

import it.polimi.progettoIngSoft.TrackingPlatform.model.Admin;
import it.polimi.progettoIngSoft.TrackingPlatform.model.User;
import it.polimi.progettoIngSoft.TrackingPlatform.model.Guest;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

@SpringBootTest
public class UserCreationTest {

    @Autowired
    private UserRepository guestRepository;

    //the following two tests check the correct registration in db of different User instances
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
        User user = guestRepository.saveAndFlush(newGuest);
        System.out.println("guestId =" + user.getId());
        user = guestRepository.findById(user.getId()).get();
        Assertions.assertTrue(user instanceof Guest);
        Assertions.assertFalse(user instanceof Admin);
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
        User user = guestRepository.saveAndFlush(newAdmin);
        System.out.println("guestId =" + user.getId());
        user = guestRepository.findById(user.getId()).get();
        Assertions.assertFalse(user instanceof Guest);
        Assertions.assertTrue(user instanceof Admin);
    }
}
