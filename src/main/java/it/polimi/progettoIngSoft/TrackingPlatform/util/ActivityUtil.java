package it.polimi.progettoIngSoft.TrackingPlatform.util;

import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Activity;

import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.RequestActivityDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.ActivityProject;

import java.util.Iterator;
import java.util.List;

public class ActivityUtil {

    public static boolean isNotInConflict (RequestActivityDto toCheck, List<ActivityProject> activities) {
        if(activities == null || activities.isEmpty() || toCheck == null || (toCheck.getEndDate() == null && toCheck.getBeginDate() == null)) {
            return true;
        }
        else {
            Iterator<ActivityProject> activityCounter = activities.iterator();
            boolean isValid = true;
            if (!toCheck.getEndDate().isAfter(toCheck.getBeginDate())) {
                isValid = false;
            }
            while (activityCounter.hasNext() && isValid) {
                Activity current = activityCounter.next();
                if (!current.getId().equals(toCheck.getId())) {
                    if (((toCheck.getEndDate() == null && toCheck.getBeginDate() != null
                            && toCheck.getBeginDate().isAfter(current.getBeginDate()) && toCheck.getBeginDate().isBefore(current.getEndDate())))
                            || ((toCheck.getEndDate() != null && toCheck.getBeginDate() == null
                            && toCheck.getEndDate().isAfter(current.getBeginDate()) && toCheck.getEndDate().isBefore(current.getEndDate())))
                            || (toCheck.getEndDate() != null && toCheck.getBeginDate() != null &&
                            ((toCheck.getEndDate().isBefore(current.getEndDate()) && toCheck.getEndDate().isAfter(current.getBeginDate()))
                            || (toCheck.getBeginDate().isBefore(current.getEndDate()) && toCheck.getBeginDate().isAfter(current.getBeginDate()))
                            || (toCheck.getBeginDate().isBefore(current.getBeginDate()) && toCheck.getEndDate().isAfter(current.getEndDate()))))
                    ) {
                        isValid = false;
                    }
                }
            }
            return isValid;
        }
    }
}
