package it.polimi.progettoIngSoft.TrackingPlatform.service;

import com.vaadin.flow.shared.GwtIncompatible;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.*;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.Token;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Project;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ProjectRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.TokenRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProjectService {


    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    public List<ProjectDto> getProjects(GetProjectsDto u){
        try {
            // get the reference of the user through the token
            Token token = tokenRepository.findByToken(u.getToken());
            User user = token.getUser();

            if (user != null) {
                // get all projects of the user
                List<Project> projects = projectRepository.getProjectByCreators(user);
                List<ProjectDto> projectDtoList = new ArrayList<ProjectDto>();
                // convert project in Dto
                for (Project project : projects) {
                    // exclude deleted projects
                    if (!project.isDeleted()) {
                        projectDtoList.add(new ProjectDto(project));
                    }
                }
                // return posts' list
                return projectDtoList;
            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ProjectDetails updateProjectDetails(UpdateProjectDetailsDto p){
        try {
            // get the project by id
            Project project = projectRepository.getProjectById(p.getIdProject());

            // modify requested data
            // if data is not passed the relative field is left unmodified
            if (p.getName() != null) project.setName(p.getName());
            if(p.getDescription() != null) project.setDescription(p.getDescription());
            if(p.getBeginDate() != null ) project.setBeginDate(p.getBeginDate());
            if(p.getEndDate() != null) project.setEndDate(p.getEndDate());

            // execute update
            project = projectRepository.save(project);
            // return updated project
            ProjectDetails pd = new ProjectDetails(project);
            return pd;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ProjectDto createProject(CreateProjectDto np){
        try{
            // get the user reference
            User user = tokenRepository.getUserByToken(np.getToken());

            // check valid user
            if (user == null){
                return null;
            }

            // get the references of the users associated with the project
            List<User> creators = new ArrayList<User>();
            List<User> admins = new ArrayList<User>();
            List<User> partecipants = new ArrayList<User>();

            // add the user creator
            creators.add(user);
            admins.add(user);
            // create project
            Project project = new Project();
            project.setName(np.getName());
            project.setDescription(np.getDescription());
            project.setBeginDate(np.getBeginDate());
            project.setEndDate(np.getEndDate());
            project.setPostImages(null);
            project.setActivities(null);
            project.setPartecipants(partecipants);
            project.setCreators(creators);
            project.setAdmins(admins);

            // save the new project
            projectRepository.save(project);
            // return project dto
            return new ProjectDto(project);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String deleteProject(DeleteProjectDto dp){
        try {
            // TODO controllo dp.getIdProject is a valid number
            // get the project's reference
            Project project = projectRepository.getProjectById(dp.getIdProject());

            if (project != null) {
                // virtual delete of the project
                project.setDeleted(true);
                projectRepository.save(project);

                return "delete successfull";
            }else {
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String changeVisibility(ChangeVisibilityDto v){
        try{
            // get the project reference
            Project project = projectRepository.getProjectById(v.getIdProject());
            if(project != null){
                // set the visibility of the project
                project.setVisible(v.isVisibility());
                // save the updated project
                if(projectRepository.save(project) != null){
                    return "project's visibility successfully changed";
                }
            }
            // error in updating
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
