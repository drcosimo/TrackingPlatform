package it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces;

import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.*;
import it.polimi.progettoIngSoft.TrackingPlatform.service.ProjectService;
import it.polimi.progettoIngSoft.TrackingPlatform.service.TokenService;
import it.polimi.progettoIngSoft.TrackingPlatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "", produces = "application/json", consumes = "application/json")
@CrossOrigin(origins="*",allowedHeaders = "*",exposedHeaders = "*")
public interface ProjectController {

    @PostMapping(path="/project/getProjects")
    public ResponseEntity<List<ProjectDto>> getProjects(@RequestBody GetProjectsDto u);

    @PostMapping(path="/project/updateProjectDetails")
    public ResponseEntity<ProjectDetails> updateProjectDetails(@RequestBody UpdateProjectDetailsDto p);

    @PostMapping(path="/project/createProject")
    public ResponseEntity<ProjectDto> createProject(@RequestBody CreateProjectDto newProject);

    @DeleteMapping(path="/project/deleteProject")
    public ResponseEntity<String> deleteProject(@RequestBody DeleteProjectDto project);

    @PostMapping(path="/project/changeVisibility")
    public ResponseEntity<String> changeVisibility(@RequestBody ChangeVisibilityDto project);
}
