package it.polimi.progettoIngSoft.TrackingPlatform.service;

import it.polimi.progettoIngSoft.TrackingPlatform.model.Activity;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ProjectActivitiesRequest;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.RequestActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.User;
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


@Service
@Transactional
public class ActivityService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private TokenService tokenService;

    //add conflicts check of activities' time period
    public ActivityDto createActivity(RequestActivityDto requestActivityDto) {
        try {
            if(tokenService.isUserEnabled(requestActivityDto.getToken())) {
                User user = tokenRepository.getUserByToken(requestActivityDto.getToken());
                Activity activity = activityRepository.save(new Activity(requestActivityDto.getName(), requestActivityDto.getDescription(), requestActivityDto.getBeginDate(), requestActivityDto.getEndDate()));
                List<Activity> existingActivities = activityRepository.getProjectActivitiesById(activity.getActivityProject().getId());
                if(user != null && StringUtils.isNotEmpty(requestActivityDto.getName()) && ActivityUtil.isNotInConflict(requestActivityDto, existingActivities)) {
                    return new ActivityDto(activity);
                }
            }
            return null;
        }
        catch (Exception e) {
            return null;
        }
    }

    //add conflicts check of activities' time period
    public ActivityDto updateActivity(RequestActivityDto updatedActivityDto) {
        try {
            if(tokenService.isUserEnabled(updatedActivityDto.getToken())) {
                User user = tokenRepository.getUserByToken(updatedActivityDto.getToken());
                Activity activity = activityRepository.findById(updatedActivityDto.getId()).get();
                //check if the user is an admin or a creator of the activity's project
                boolean found = false;
                Iterator creatorsCounter = activity.getActivityProject().getCreators().iterator();
                Iterator adminsCounter = activity.getActivityProject().getAdmins().iterator();
                while (creatorsCounter.hasNext() && adminsCounter.hasNext() && !found) {
                    if (user.equals(creatorsCounter.next()) || user.equals(adminsCounter.next())) {
                        found = true;
                    }
                }
                if (!found) {
                    return null;
                }
                //if the user has access to the project and the modified activity is not in conflict with any other activity
                else {
                    List<Activity> existingActivities = activityRepository.getProjectActivitiesById(activity.getActivityProject().getId());
                    if (StringUtils.isNotEmpty(updatedActivityDto.getName()) && ActivityUtil.isNotInConflict(updatedActivityDto, existingActivities)) {
                        activity.setName(updatedActivityDto.getName());
                        if (!updatedActivityDto.getDescription().isEmpty()) {
                            activity.setDescription(updatedActivityDto.getDescription());
                        }
                        activity.setBeginDate(updatedActivityDto.getBeginDate());
                        activity.setEndDate(updatedActivityDto.getEndDate());
                        activityRepository.save(activity);
                        return new ActivityDto(activity);
                    } else {
                        return null;
                    }
                }
            }
            return null;
        }
        catch (Exception e) {
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
            return null;
        }
    }
}
