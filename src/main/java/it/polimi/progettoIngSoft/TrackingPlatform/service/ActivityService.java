package it.polimi.progettoIngSoft.TrackingPlatform.service;

import it.polimi.progettoIngSoft.TrackingPlatform.model.Activity;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ProjectActivitiesRequest;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.RequestActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.Project;
import it.polimi.progettoIngSoft.TrackingPlatform.model.User;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ActivityRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ProjectRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.TokenRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;


@Service
@Transactional
public class ActivityService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ActivityDto createActivity(RequestActivityDto requestActivityDto) {
        try {
            User user = tokenRepository.getUserByToken(requestActivityDto.getToken());
            if(user != null && StringUtils.isNotEmpty(requestActivityDto.getName()) &&
                    (requestActivityDto.getBeginDate() == null || requestActivityDto.getEndDate() == null || requestActivityDto.getBeginDate().isBefore(requestActivityDto.getEndDate()))) {
                Activity activity = activityRepository.save(new Activity(requestActivityDto.getName(), requestActivityDto.getDescription(), requestActivityDto.getBeginDate(), requestActivityDto.getEndDate()));
                return new ActivityDto(activity);
            }
            else  {
                return null;
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    public ActivityDto updateActivity(RequestActivityDto updatedActivity) {
        try {
            User user = tokenRepository.getUserByToken(updatedActivity.getToken());
            Activity activity = activityRepository.findById(updatedActivity.getId()).get();
            //check if the user is an admin or a creator of the activity
            boolean found = false;
            Iterator creatorsCounter = activity.getActivityProject().getCreators().iterator();
            Iterator adminsCounter = activity.getActivityProject().getAdmins().iterator();
            while(creatorsCounter.hasNext() && adminsCounter.hasNext() && !found) {
                if (user.equals(creatorsCounter.next()) || user.equals(adminsCounter.next())) {
                    found = true;
                }
            }
            if(!found) {
                return null;
            }
            //if the user has access to the activity
            else if (StringUtils.isNotEmpty(updatedActivity.getName())) {
                activity.setName(updatedActivity.getName());
                if(!updatedActivity.getDescription().isEmpty()) {
                    activity.setDescription(updatedActivity.getDescription());
                }
                //check coherence of begin/end dates
                if(updatedActivity.getBeginDate() == null || updatedActivity.getEndDate() == null || updatedActivity.getBeginDate().isBefore(updatedActivity.getEndDate())) {
                    activity.setBeginDate(updatedActivity.getBeginDate());
                    activity.setEndDate(updatedActivity.getEndDate());
                }
                activityRepository.save(activity);
                return new ActivityDto(activity);
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    public List<ActivityDto> getActivitiesFromProject(ProjectActivitiesRequest projectActivitiesRequest) {
        try {
            User user = tokenRepository.getUserByToken(projectActivitiesRequest.getToken());
            if(user != null && projectActivitiesRequest.getProjectId() != null) {
                Project project = projectRepository.findById(projectActivitiesRequest.getProjectId()).get();
                //List<Activity> projectActivities = activityRepository;
                return null;
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            return null;
        }
    }
}
