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
            // autenticazione
            if(tokenService.isUserEnabled(u.getToken()) && u != null){
                // restituisco i post all'utente
                List<ProjectDto> projects = projectService.getProjects(u);
                if(projects != null){
                    return new ResponseEntity<>(projects,HttpStatus.OK);
                }else{
                    return exceptionReturn(RESPONSE_NULL);
                }
            }else{
                // utente non loggato, operazione illegale
                return exceptionReturn(PRECONDITIONS_FAILED);
            }
        }catch(Exception e){
            e.printStackTrace();
            // errore interno
            return exceptionReturn("");
        }
    }

    @Override
    public ResponseEntity<ProjectDetails> updateProjectDetails(UpdateProjectDetailsDto p){
        try{
            // controllo che la sessione sia valida
            if(tokenService.isUserEnabled(p.getToken()) && p != null){
                // restituisco il progetto aggiornato all'utente
                ProjectDetails pd = projectService.updateProjectDetails(p);

                if (pd != null){
                    return new ResponseEntity<>(pd,HttpStatus.OK);
                }else{
                    return exceptionReturn(RESPONSE_NULL);
                }
            }else{
                // utente non loggato, operazione illegale
                return exceptionReturn(PRECONDITIONS_FAILED);
            }
        }catch (Exception e){
            return exceptionReturn("");
        }
    }

    @Override
    public ResponseEntity<ProjectDto> createProject(CreateProjectDto newProject){
        try{
            // controllo che la sessione sia valida
            if(tokenService.isUserEnabled(newProject.getToken())){
                // effettuo la creazione del progetto e restituisco il dto per la visualizzazione
                ProjectDto project = projectService.createProject(newProject);
                if (project != null){
                    return new ResponseEntity<>(project,HttpStatus.OK);
                }else{
                    return exceptionReturn(RESPONSE_NULL);
                }
            }else{
                // utente non loggato, operazione illegale
                return exceptionReturn(PRECONDITIONS_FAILED);
            }
        }catch(Exception e){
            e.printStackTrace();
            return exceptionReturn("");
        }
    }

    public ResponseEntity<String> deleteProject(DeleteProjectDto project){
        try{
            // autenticazione
            if(tokenService.isUserEnabled(project.getToken())){
                // effettuo il delete del progetto
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

