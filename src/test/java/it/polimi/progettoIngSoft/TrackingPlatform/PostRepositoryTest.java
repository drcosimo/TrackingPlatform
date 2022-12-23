package it.polimi.progettoIngSoft.TrackingPlatform;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.ActivityProject;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Project;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.Guest;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ActivityRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.PostRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ProjectRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    private void init () {
        Guest niccoGuest = new Guest("nicco", "deca", "ndc", "nicco@deca.com", "pass", Date.valueOf(LocalDate.of(2001, 10, 27)), "yes");
        ActivityProject act1 = new ActivityProject("act1", "desc", Instant.now(), Instant.now().plus(1, ChronoUnit.DAYS), null, niccoGuest);
        ActivityProject act2 = new ActivityProject("act2", "desc", Instant.now().plus(1, ChronoUnit.DAYS), Instant.now().plus(2, ChronoUnit.DAYS), null, niccoGuest);
        Project proj = new Project();
        proj.setName("projectName");
        proj.setAdmins(List.of(niccoGuest));
        proj.setCreators(List.of(niccoGuest));
        proj.setActivities(List.of(act1, act2));
        userRepository.saveAndFlush(niccoGuest);
        activityRepository.saveAndFlush(act1);
        activityRepository.saveAndFlush(act2);
        projectRepository.saveAndFlush(proj);
    }

    @Test
    public void getProjectByActivityTest() {
        ActivityProject firstActivity = (ActivityProject) activityRepository.findById(Long.valueOf("2")).get();
        Project toRetrieve = projectRepository.findFirstByActivitiesContains(firstActivity);
        Assertions.assertEquals("projectName", toRetrieve.getName());
        Assertions.assertEquals("nicco", postRepository.getCreatorsById(toRetrieve.getId()).get(0).getName());
    }
}
