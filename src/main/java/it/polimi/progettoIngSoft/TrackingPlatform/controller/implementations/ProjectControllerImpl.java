package it.polimi.progettoIngSoft.TrackingPlatform.controller.implementations;

import it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces.ProjectController;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.*;
import it.polimi.progettoIngSoft.TrackingPlatform.service.ProjectService;
import it.polimi.progettoIngSoft.TrackingPlatform.service.TokenService;
import it.polimi.progettoIngSoft.TrackingPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "", produces = "application/json", consumes = "application/json")
@CrossOrigin(origins="*",allowedHeaders = "*",exposedHeaders = "*")
public class ProjectControllerImpl implements ProjectController {

    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;
    @Autowired
    TokenService tokenService;

    private final String PRECONDITIONS_FAILED = "preconditions failed";
    private final String RESPONSE_NULL ="response null";

    @Override
    public ResponseEntity<List<ProjectDto>> getProjects(GetProjectsDto u){
        try{
            // preconditions
            if(tokenService.isUserEnabled(u.getToken()) && u != null){
                // return posts to the user
                List<ProjectDto> projects = projectService.getProjects(u);
                if(projects != null){
                    return new ResponseEntity<>(projects,HttpStatus.OK);
                }else{
                    return exceptionReturn(RESPONSE_NULL);
                }
            }else{
                // preconditions failed
                return exceptionReturn(PRECONDITIONS_FAILED);
            }
        }catch(Exception e){
            e.printStackTrace();
            // unexpected error
            return exceptionReturn("");
        }
    }

    @Override
    public ResponseEntity<ProjectDetails> updateProjectDetails(UpdateProjectDetailsDto p){
        try{
            // preconditions
            if(tokenService.isUserEnabled(p.getToken()) && p != null){
                //return updated project to user
                ProjectDetails pd = projectService.updateProjectDetails(p);
                // check non nullity
                if (pd != null){
                    return new ResponseEntity<>(pd,HttpStatus.OK);
                }else{
                    return exceptionReturn(RESPONSE_NULL);
                }
            }else{
                // preconditions failed
                return exceptionReturn(PRECONDITIONS_FAILED);
            }
        }catch (Exception e){
            return exceptionReturn("");
        }
    }

    @Override
    public ResponseEntity<ProjectDto> createProject(CreateProjectDto newProject){
        try{
            // preconditions
            if(tokenService.isUserEnabled(newProject.getToken()) && newProject != null){
                // create project and return dto for visualization
                ProjectDto project = projectService.createProject(newProject);
                if (project != null){
                    return new ResponseEntity<>(project,HttpStatus.OK);
                }else{
                    return exceptionReturn(RESPONSE_NULL);
                }
            }else{
                // preconditions failed
                return exceptionReturn(PRECONDITIONS_FAILED);
            }
        }catch(Exception e){
            e.printStackTrace();
            return exceptionReturn("");
        }
    }

    public ResponseEntity<String> deleteProject(DeleteProjectDto project){
        try{
            // authentication
            if(tokenService.isUserEnabled(project.getToken()) && project != null){
                // delete of the project
                String response = projectService.deleteProject(project);
                if (response != null){
                    return new ResponseEntity<>(response,HttpStatus.OK);
                }else{
                    return exceptionReturn(RESPONSE_NULL);
                }
            }else{
                return exceptionReturn(PRECONDITIONS_FAILED);
            }
        }catch(Exception e){
            e.printStackTrace();
            return exceptionReturn("");
        }
    }

    public ResponseEntity<String> changeVisibility(ChangeVisibilityDto v){
        try{
            // authentication
            if(tokenService.isUserEnabled(v.getToken()) && v != null){
                // change visibility
                String response = projectService.changeVisibility(v);
                if (response != null){
                    return new ResponseEntity<>(response,HttpStatus.OK);
                }else{
                    return exceptionReturn(RESPONSE_NULL);
                }
            }else{
                return exceptionReturn(PRECONDITIONS_FAILED);
            }
        }catch(Exception e){
            e.printStackTrace();
            return exceptionReturn("");
        }
    }

    private ResponseEntity exceptionReturn(String errorMessage) {
        switch (errorMessage) {
            case PRECONDITIONS_FAILED : {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            case RESPONSE_NULL : {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            default: return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

