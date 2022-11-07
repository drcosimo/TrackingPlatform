package it.polimi.progettoIngSoft.TrackingPlatform.service;

import it.polimi.progettoIngSoft.TrackingPlatform.model.Activity;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.ActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.RequestActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.User;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ActivityRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.TokenRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivityService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public ActivityDto createActivity(RequestActivityDto requestActivityDto) {
        try {
            User user = tokenRepository.getUserByToken(requestActivityDto.getToken());
            if(user != null && StringUtils.isNotEmpty(requestActivityDto.getName())) {
                Activity activity = activityRepository.save(new Activity(requestActivityDto.getName(), requestActivityDto.getDescription(), requestActivityDto.getBeginDate(), requestActivityDto.getEndDate()));
                if(activity != null) {
                    return new ActivityDto(activity);
                }
                else return null;
            }
            else return null;
        }
        catch (Exception e) {
            return null;
        }
    }

    public ActivityDto updateActivity(RequestActivityDto updatedActivity) {
        try {
            User user = tokenRepository.getUserByToken(updatedActivity.getToken());
            Activity activity = activityRepository.findById(updatedActivity.getId()).get();
            //TODO
            return null;
        }
        catch (Exception e) {
            return null;
        }
    }
}
