package it.polimi.progettoIngSoft.TrackingPlatform.service;

import com.vaadin.flow.component.html.Pre;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Activity;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ProjectActivitiesRequest;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.RequestActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.ActivityPost;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.ActivityProject;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Project;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ActivityPostRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ActivityProjectRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ActivityRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ProjectRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.TokenRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.util.ActivityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Iterator;
import java.util.List;
import com.google.common.base.Preconditions;


@Service
@Transactional
public class ActivityService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ActivityProjectRepository activityProjectRepository;

    @Autowired
    private ActivityPostRepository activityPostRepository;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private TokenService tokenService;

    public ActivityDto createActivity(RequestActivityDto requestActivityDto) {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(requestActivityDto.getName()), "activity name cannot be empty");
            Preconditions.checkArgument(
                    requestActivityDto.getVehiclesIds() == null || vehicleService.checkVehiclesExistence(requestActivityDto.getVehiclesIds()),
                    "vehicles selected are wrong");
            User user = tokenRepository.getUserByToken(requestActivityDto.getToken());
            Preconditions.checkNotNull(user, "user not found");
            //different cases based on the type of request (if it's an activityProject or an activityPost creation)

            //activityProject case
            if (requestActivityDto.getProjectId() != null && requestActivityDto.getProjectId() > 0) {
                Project project = projectRepository.findById(requestActivityDto.getProjectId()).get();
                //check if the user is an admin of the activity's project
                boolean found = false;
                Iterator adminsIterator = project.getAdmins().iterator();
                while (adminsIterator.hasNext() && !found) {
                    if (user.equals(adminsIterator.next())) {
                        found = true;
                    }
                }
                Preconditions.checkArgument(found, "you don't have permissions to create an activity in this project");
                List<ActivityProject> existingActivities = projectRepository.getProjectActivitiesById(requestActivityDto.getProjectId());
                Preconditions.checkArgument(ActivityUtil.isNotInConflict(requestActivityDto, existingActivities), "the new activity is in conflict with another activity");

                ActivityProject activity = activityProjectRepository.save(new ActivityProject(requestActivityDto.getName(), requestActivityDto.getDescription(), requestActivityDto.getBeginDate(), requestActivityDto.getEndDate(), vehicleService.getAllByIds(requestActivityDto.getVehiclesIds()), user));
                project.getActivities().add(activity);
                projectRepository.save(project);
                return new ActivityDto(activity);

            }
            //activityPost case
            else if(requestActivityDto.getProjectId() == null) {
                 ActivityPost activity = activityPostRepository.save(new ActivityPost(requestActivityDto.getName(), requestActivityDto.getDescription(), requestActivityDto.getBeginDate(), requestActivityDto.getEndDate(), vehicleService.getAllByIds(requestActivityDto.getVehiclesIds()), user));
                return new ActivityDto(activity);
            }
            else {
                return null;
            }

        }
        catch (Exception e) {
            switch (e.getMessage()) {
                case "activity name cannot be empty": {
                    return new ActivityDto("activity name cannot be empty");
                }
                case "vehicles selected are wrong": {
                    return new ActivityDto("vehicles selected are wrong");
                }
                case "user not found": {
                    return new ActivityDto("user not found");
                }
                case "the new activity is in conflict with another activity": {
                    return new ActivityDto("the new activity is in conflict with another activity");
                }
                case "you don't have permissions to create an activity in this project": {
                    return new ActivityDto(("you don't have permissions to create an activity in this project"));
                }
                default: return null;
            }
        }
    }

    //TODO : refactor all this method with Preconditions
    public ActivityDto updateActivity(RequestActivityDto updatedActivityDto) {
        try {
            Preconditions.checkArgument(tokenService.isUserEnabled(updatedActivityDto.getToken()));
            if(tokenService.isUserEnabled(updatedActivityDto.getToken()) && StringUtils.isNotEmpty(updatedActivityDto.getName()) &&
                    (updatedActivityDto.getVehiclesIds() == null || vehicleService.checkVehiclesExistence(updatedActivityDto.getVehiclesIds()))) {
                User user = tokenRepository.getUserByToken(updatedActivityDto.getToken());
                Activity activity = activityRepository.findById(updatedActivityDto.getId()).get();
                //check if the user is an admin of the activity
                boolean found = false;
                Iterator adminsIterator = activity.getAdmins().iterator();
                while (adminsIterator.hasNext() && !found) {
                    if (user.equals(adminsIterator.next())) {
                        found = true;
                    }
                }
                //different cases based on the type of request (if it's an activityProject or an activityPost creation)

                //activityProject case
                if (found && updatedActivityDto.getProjectId() != null && updatedActivityDto.getProjectId() > 0) {
                    Project project = projectRepository.findById(updatedActivityDto.getProjectId()).get();
                    //check if the project contains that activity
                    boolean isContained = false;
                    Iterator<ActivityProject> activityIterator = project.getActivities().iterator();
                    while (activityIterator.hasNext() && !isContained) {
                        if(activityIterator.next().getId().equals(activity.getId())) {
                            isContained = true;
                        }
                    }
                    Preconditions.checkArgument(isContained, "the activity is not part of the project");
                    //if the activity matches with its project

                    //if the activity doesn't match with its project

                }

                else if (found && updatedActivityDto.getProjectId() == null) {

                    //if the user has access to the project and the modified activity is not in conflict with any other activity

                    List<ActivityProject> existingActivities = projectRepository.getProjectActivitiesById(activity.getId());
                    if (ActivityUtil.isNotInConflict(updatedActivityDto, existingActivities)) {
                        activity.setName(updatedActivityDto.getName());
                        if (!updatedActivityDto.getDescription().isEmpty()) {
                            activity.setDescription(updatedActivityDto.getDescription());
                        }
                        activity.setBeginDate(updatedActivityDto.getBeginDate());
                        activity.setEndDate(updatedActivityDto.getEndDate());
                        activityRepository.save(activity);
                        return new ActivityDto(activity);
                    } else {

                    }
                }
            }
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ActivityDto> getActivitiesFromProject(ProjectActivitiesRequest projectActivitiesRequest) {
        try {
            if(tokenService.isUserEnabled(projectActivitiesRequest.getToken())) {
                User user = tokenRepository.getUserByToken(projectActivitiesRequest.getToken());
                if (user != null && projectActivitiesRequest.getProjectId() != null) {
                    List<Activity> projectActivities = activityRepository.getProjectActivitiesById(projectActivitiesRequest.getProjectId());
                    if (!projectActivities.isEmpty()) {
                        Iterator<Activity> activityCounter = projectActivities.listIterator();
                        List<ActivityDto> returnList = List.of();
                        while (activityCounter.hasNext()) {
                            returnList.add(new ActivityDto(activityCounter.next()));
                        }
                        return returnList;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
