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

    // classe per la gestione dei controlli ed eccezioni
    public List<ProjectDto> getProjects(GetProjectsDto u){
        try {
            // ottengo il riferimento all'utente passando per il token
            Token token = tokenRepository.findByToken(u.getToken());
            User user = token.getUser();

            if (user != null) {
                // ottengo tutti i progetti associati all'utente
                List<Project> projects = projectRepository.getProjectByCreators(user);
                List<ProjectDto> projectDtoList = new ArrayList<ProjectDto>();
                // conversione project in projectDto
                for (Project project : projects) {
                    projectDtoList.add(new ProjectDto(project));
                }
                // restituisco la lista di post
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
            // ottengo il progetto a cui fa riferimento il DTO
            Project project = projectRepository.getProjectById(p.getIdProject());

            // associo i dati modificati
            project.setName(p.getName());
            project.setDescription(p.getDescription());
            project.setBeginDate(p.getBeginDate());
            project.setEndDate(p.getEndDate());

            // eseguo l'update
            project = projectRepository.save(project);
            // update effettuato, restituisco il progetto aggiornato
            ProjectDetails pd = new ProjectDetails(project);
            return pd;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<ProjectDto> createProject(CreateProjectDto np){
        try{
            // ottengo il riferimento all utente
            User user = tokenRepository.getUserByToken(np.getToken());

            // controllo utente valido
            if (user == null){
                return null;
            }

            // ottengo i riferimenti agli utenti coinvolti nel progetto
            List<User> creators = new ArrayList<User>();
            List<User> admins = new ArrayList<User>();
            List<User> partecipants = new ArrayList<User>();

            // aggiungo l utente che sta creando il progetto
            creators.add(user);
            admins.add(user);
            // creo il progetto
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

            // salvo il nuovo progetto sul db
            projectRepository.save(project);
            // restituisco il progetto
            return new ResponseEntity<>(new ProjectDto(project),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteProject(DeleteProjectDto dp){
        try {
            // TODO controllo dp.getIdProject is a valid number
            // ottengo il riferimento al progetto
            Project project = projectRepository.getProjectById(dp.getIdProject());

            if (project != null) {
                // effettuo il delete del progetto
                project.setDeleted(true);
                projectRepository.save(project);

                return new ResponseEntity<>("cancellazione effettuata con successo", HttpStatus.OK);
            }

            return new ResponseEntity<>("progetto non trovato",HttpStatus.BAD_REQUEST);

        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("errore inaspettato",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
