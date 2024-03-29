package it.polimi.progettoIngSoft.TrackingPlatform.repository;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.ActivityProject;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Project;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("select p.activities from Project p where p.id = :idProject")
    public List<ActivityProject> getProjectActivitiesById(Long idProject);

    Project getProjectById(Long idProject);

    public List<Project> getProjectByCreators(User user);

    public Project findFirstByActivitiesContains(ActivityProject activityProject);


}
