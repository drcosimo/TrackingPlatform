package it.polimi.progettoIngSoft.TrackingPlatform.service;

import com.google.common.base.Preconditions;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.UpdatePermissionsDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Activity;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.ActivityProject;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Post;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Project;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ActivityProjectRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ActivityRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.PostRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.ProjectRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.TokenRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ActivityProjectRepository activityProjectRepository;


    private final String USER_DOES_NOT_HAVE_PERMISSIONS_TO_ = "user does not have permission to ";
    private final String ACTIVITY = "activity";
    private final String PROJECT = "project";
    private final String POST = "post";
    private final String USER_NOT_FOUND = "user not found";
    private final String THE_POST_ID_CANNOT_BE_NULL = "the post id cannot be null";
    private final String THE_LIST_OF_USERNAMES_IS_EMPTY = "the list of usernames is empty";
    private final String ADD_CREATORS_TO_THIS_ = "add creators to this ";
    private final String NOT_EVERY_SPECIFIED_USERNAME_EXISTS = "not every specified username exists";
    private final String REMOVE_CREATORS_FROM_THIS_ = "remove creators from this ";
    private final String ADD_ADMINS_TO_THIS_ = "add admins to this ";
    private final String REMOVE_ADMINS_FROM_THIS_ = "remove admins from this ";
    private final String ADD_PARTECIPANTS_TO_THIS_ = "add partecipants to this ";
    private final String REMOVE_PARTECIPANTS_FROM_THIS_ = "remove partecipants from this ";
    private final String POST_NOT_FOUND = "post not found";




    //TODO HAVE TO FORK ActivityProject AND OTHER POSTS CASES




    //the system ignores if the user wants to add a creator that already exists
    public String addCreator(UpdatePermissionsDto request) {
        try {
            //request params check
            Preconditions.checkNotNull(request.getPostId(), THE_POST_ID_CANNOT_BE_NULL);
            Preconditions.checkArgument(!request.getUsernames().isEmpty(), THE_LIST_OF_USERNAMES_IS_EMPTY);
            //check that the user exists
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            Preconditions.checkNotNull(user.getId(), USER_NOT_FOUND);
            //retrieving the Post
            Post post = postRepository.findById(request.getPostId()).get();
            //fetching creators
            post.setCreators(postRepository.getCreatorsById(post.getId()));
            //checking permissions
            Preconditions.checkArgument(post.getCreators().contains(user), USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_CREATORS_TO_THIS_ + POST);
            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    NOT_EVERY_SPECIFIED_USERNAME_EXISTS);
            //retrieving users instances to add
            List<User> usersToAdd = userRepository.findByUsernameIn(usernames);
            post.setAdmins(postRepository.getAdminsById(post.getId()));
            //add every user to its new permissions
            for (User u : usersToAdd) {
                if (!post.getCreators().contains(u)) {
                    post.getCreators().add(u);
                }
                if (!post.getAdmins().contains(u)) {
                    post.getAdmins().add(u);
                }
            }

            //if the post is a project, it is necessary to add new users in projectAdmins list and in every permission list of every ActivityProject contained in the project
            if (post instanceof Project) {
                Project project = (Project) post;
                //fetching the activities
                project.setActivities(projectRepository.getProjectActivitiesById(post.getId()));
                int counter = 0;
                //fetching creators and admins of the activities
                for (ActivityProject act : project.getActivities()) {
                    project.getActivities().get(counter).setCreators(postRepository.getCreatorsById(act.getId()));
                    project.getActivities().get(counter).setAdmins(postRepository.getAdminsById(act.getId()));
                }
                //for every ActivityProject in the project, add every user to creators' and admins' list
                for (int j = 0 ; j < project.getActivities().size() ; j++) {
                    for (User u : usersToAdd) {
                        if (!project.getActivities().get(j).getCreators().contains(u)) {
                            project.getActivities().get(j).getCreators().add(u);
                        }
                        if (!project.getActivities().get(j).getAdmins().contains(u)) {
                            project.getActivities().get(j).getAdmins().add(u);
                        }
                    }
                    activityRepository.save(project.getActivities().get(j));
                }
                projectRepository.save(project);
            }
            postRepository.save(post);
            return "";
        } catch (Exception e) {
            switch (e.getMessage()) {
                case USER_NOT_FOUND: {
                    return USER_NOT_FOUND;
                }
                case THE_POST_ID_CANNOT_BE_NULL : {
                    return THE_POST_ID_CANNOT_BE_NULL;
                }
                case THE_LIST_OF_USERNAMES_IS_EMPTY : {
                    return THE_LIST_OF_USERNAMES_IS_EMPTY;
                }
                case USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_CREATORS_TO_THIS_ + POST : {
                    return USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_CREATORS_TO_THIS_ + POST;
                }
                case NOT_EVERY_SPECIFIED_USERNAME_EXISTS : {
                    return NOT_EVERY_SPECIFIED_USERNAME_EXISTS;
                }
                default:
                    return null;
            }
        }
    }

    public String removeCreator(UpdatePermissionsDto request) {
        try {
            //request params check
            Preconditions.checkNotNull(request.getPostId(), THE_POST_ID_CANNOT_BE_NULL);
            Preconditions.checkArgument(!request.getUsernames().isEmpty(), THE_LIST_OF_USERNAMES_IS_EMPTY);
            //check that the user exists
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            Preconditions.checkNotNull(user.getId(), USER_NOT_FOUND);
            //retrieving the Post
            Post post = postRepository.findById(request.getPostId()).get();
            //fetching creators
            post.setCreators(postRepository.getCreatorsById(post.getId()));
            //checking permissions
            Preconditions.checkArgument(post.getCreators().contains(user), USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_CREATORS_FROM_THIS_ + POST);
            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    NOT_EVERY_SPECIFIED_USERNAME_EXISTS);
            //retrieving users instances to remove
            List<User> usersToRemove = userRepository.findByUsernameIn(usernames);
            //fetching post admins
            post.setAdmins(postRepository.getAdminsById(post.getId()));

            //removes from usersToRemove all users having the permission of admin in the project
            //because ad admin of the project cannot be deleted from any under-ActivityProject permissions
            if (post instanceof ActivityProject) {
               Project proj = projectRepository.findFirstByActivitiesContains((ActivityProject) post);
               proj.setAdmins(postRepository.getAdminsById(proj.getId()));
               for (User u : usersToRemove) {
                   if (proj.getAdmins().contains(u)) {
                       usersToRemove.remove(u);
                   }
               }
            }

            //eliminates the permission for the selected users
            for (User u : usersToRemove) {
                post.getCreators().remove(u);
            }
            //checks that not every creator has been removed
            //else re-add this user as the only creator
            if (post.getCreators().isEmpty()) {
                post.getCreators().add(user);
            }

            postRepository.save(post);
            return "";
        } catch (Exception e) {
            switch (e.getMessage()) {
                case THE_POST_ID_CANNOT_BE_NULL : {
                    return THE_POST_ID_CANNOT_BE_NULL;
                }
                case THE_LIST_OF_USERNAMES_IS_EMPTY : {
                    return THE_LIST_OF_USERNAMES_IS_EMPTY;
                }
                case USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_CREATORS_FROM_THIS_ + POST: {
                    return USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_CREATORS_FROM_THIS_ + POST;
                }
                case USER_NOT_FOUND: {
                    return USER_NOT_FOUND;
                }
                case NOT_EVERY_SPECIFIED_USERNAME_EXISTS : {
                    return NOT_EVERY_SPECIFIED_USERNAME_EXISTS;
                }
                default:
                    return null;
            }
        }
    }

    public String addAdmin(UpdatePermissionsDto request) {
        try {
            //request params check
            Preconditions.checkNotNull(request.getPostId(), THE_POST_ID_CANNOT_BE_NULL);
            Preconditions.checkArgument(!request.getUsernames().isEmpty(), THE_LIST_OF_USERNAMES_IS_EMPTY);
            //check that the user exists
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            Preconditions.checkNotNull(user.getId(), USER_NOT_FOUND);
            //retrieving the Post
            Post post = postRepository.findById(request.getPostId()).get();
            //fetching admins
            post.setAdmins(postRepository.getAdminsById(post.getId()));
            //checking permissions
            Preconditions.checkArgument(post.getAdmins().contains(user), USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_CREATORS_TO_THIS_ + POST);
            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    NOT_EVERY_SPECIFIED_USERNAME_EXISTS);
            //retrieving users instances to add
            List<User> usersToAdd = userRepository.findByUsernameIn(usernames);
            //add every user to its new permissions
            for (User u : usersToAdd) {
                if (!post.getAdmins().contains(u)) {
                    post.getAdmins().add(u);
                }
            }

            //if the post is a project, it is necessary to add new users in projectAdmins list and in every permission list of every ActivityProject contained in the project
            if (post instanceof Project) {
                Project project = (Project) post;
                //fetching the activities
                project.setActivities(projectRepository.getProjectActivitiesById(post.getId()));
                int counter = 0;
                //fetching creators and admins of the activities
                for (ActivityProject act : project.getActivities()) {
                    project.getActivities().get(counter).setCreators(postRepository.getCreatorsById(act.getId()));
                    project.getActivities().get(counter).setAdmins(postRepository.getAdminsById(act.getId()));
                    counter++;
                }
                //for every ActivityProject in the project, add every user to creators' and admins' list
                for (int j = 0 ; j < project.getActivities().size() ; j++) {
                    for (User u : usersToAdd) {
                        if (!project.getActivities().get(j).getCreators().contains(u)) {
                            project.getActivities().get(j).getCreators().add(u);
                        }
                        if (!project.getActivities().get(j).getAdmins().contains(u)) {
                            project.getActivities().get(j).getAdmins().add(u);
                        }
                    }
                    activityRepository.save(project.getActivities().get(j));
                }
                projectRepository.save(project);
            }
            postRepository.save(post);
            return "";
        } catch (Exception e) {
            switch (e.getMessage()) {
                case USER_NOT_FOUND: {
                    return USER_NOT_FOUND;
                }
                case THE_POST_ID_CANNOT_BE_NULL : {
                    return THE_POST_ID_CANNOT_BE_NULL;
                }
                case THE_LIST_OF_USERNAMES_IS_EMPTY : {
                    return THE_LIST_OF_USERNAMES_IS_EMPTY;
                }
                case USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_CREATORS_TO_THIS_ + POST : {
                    return USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_CREATORS_TO_THIS_ + POST;
                }
                case NOT_EVERY_SPECIFIED_USERNAME_EXISTS : {
                    return NOT_EVERY_SPECIFIED_USERNAME_EXISTS;
                }
                default:
                    return null;
            }
        }
    }



    //TODO PROBLEM: what happend if a project admin is removed?? what about all its activities permissions?

    //TODO i did this:
    // we decided that we have to add a registry that remembers the activities that a user created
    // and when he is removed from project admin permission, he still has all permissions on the created ActivityProject

    //TODO we have to do another method that eliminates user's permissions from the Project and all it's activities (new feature)

    //TODO we could leave those permissions or leave only the admin permissions of the activities


    public String removeAdmin(UpdatePermissionsDto request) {
        try {
            //request params check
            Preconditions.checkNotNull(request.getPostId(), THE_POST_ID_CANNOT_BE_NULL);
            Preconditions.checkArgument(!request.getUsernames().isEmpty(), THE_LIST_OF_USERNAMES_IS_EMPTY);
            //check that the user exists
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            Preconditions.checkNotNull(user.getId(), USER_NOT_FOUND);
            //retrieving the Post
            Post post = postRepository.findById(request.getPostId()).get();
            //fetching admins
            post.setAdmins(postRepository.getAdminsById(post.getId()));
            //checking permissions
            Preconditions.checkArgument(post.getAdmins().contains(user), USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_CREATORS_FROM_THIS_ + POST);
            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    NOT_EVERY_SPECIFIED_USERNAME_EXISTS);
            //retrieving users instances to remove
            List<User> usersToRemove = userRepository.findByUsernameIn(usernames);
            //fetching post's admins
            post.setAdmins(postRepository.getAdminsById(post.getId()));

            //removes from usersToRemove all users having the permission of admin in the project
            //because ad admin of the project cannot be deleted from any under-ActivityProject permissions
            if (post instanceof ActivityProject) {
                Project proj = projectRepository.findFirstByActivitiesContains((ActivityProject) post);
                proj.setAdmins(postRepository.getAdminsById(proj.getId()));
                for (User u : usersToRemove) {
                    if (proj.getAdmins().contains(u)) {
                        usersToRemove.remove(u);
                    }
                }
            }

            //eliminates the permission for the selected users
            for (User u : usersToRemove) {
                post.getAdmins().remove(u);
            }

            //for every ActivityProject in the project, add every user to creators' and admins' list
            if (post instanceof Project) {
                Project project = (Project) post;
                //fetching ActivityProject
                project.setActivities(projectRepository.getProjectActivitiesById(post.getId()));
                int counter = 0;
                //fetching creators and admins of every ActivityProject
                for (ActivityProject act : project.getActivities()) {
                    project.getActivities().get(counter).setCreators(postRepository.getCreatorsById(act.getId()));
                    project.getActivities().get(counter).setAdmins(postRepository.getAdminsById(act.getId()));
                    project.getActivities().get(counter).setCreator(activityProjectRepository.getRealCreatorById(act.getId()));
                    counter++;
                }
                for (int j = 0 ; j < project.getActivities().size() ; j++) {
                    for (User u : usersToRemove) {
                        //removes every user's permission from the activity only if he is not the real creator of it
                        if (!project.getActivities().get(j).getCreator().equals(u)) {
                            project.getActivities().get(j).getCreators().remove(u);
                            project.getActivities().get(j).getAdmins().remove(u);
                        }
                    }
                    activityRepository.save(project.getActivities().get(j));
                }
            }

            postRepository.save(post);
            return "";
        } catch (Exception e) {
            switch (e.getMessage()) {
                case THE_POST_ID_CANNOT_BE_NULL : {
                    return THE_POST_ID_CANNOT_BE_NULL;
                }
                case THE_LIST_OF_USERNAMES_IS_EMPTY : {
                    return THE_LIST_OF_USERNAMES_IS_EMPTY;
                }
                case USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_CREATORS_FROM_THIS_ + POST: {
                    return USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_CREATORS_FROM_THIS_ + POST;
                }
                case USER_NOT_FOUND: {
                    return USER_NOT_FOUND;
                }
                case NOT_EVERY_SPECIFIED_USERNAME_EXISTS : {
                    return NOT_EVERY_SPECIFIED_USERNAME_EXISTS;
                }
                default:
                    return null;
            }
        }
    }

    //TODO fai da qua in poi
    public String addPartecipant(UpdatePermissionsDto request) {
        try {
            Preconditions.checkNotNull(request.getActivityId(), THE_ACTIVITY_ID_CANNOT_BE_NULL);
            Preconditions.checkArgument(!request.getUsernames().isEmpty(), THE_LIST_OF_USERNAMES_IS_EMPTY);
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            Activity activity = activityRepository.findById(request.getActivityId()).get();
            //check if the user has permissions to add a partecipant
            Preconditions.checkArgument(activity.getAdmins().contains(user), USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_PARTECIPANTS_TO_THIS_ACTIVITY);

            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    NOT_EVERY_SPECIFIED_USERNAME_EXISTS);

            //the system ignores if the user wants to add a partecipant that already exists
            List<User> usersToAdd = userRepository.findByUsernameIn(usernames);
            for (User u : usersToAdd) {
                //check if the user is not already a creator
                if (!activity.getPartecipants().contains(u)) {
                    activity.getPartecipants().add(u);
                }
            }
            activityRepository.save(activity);
            return "";
        } catch (Exception e) {
            switch (e.getMessage()) {
                case THE_ACTIVITY_ID_CANNOT_BE_NULL: {
                    return THE_ACTIVITY_ID_CANNOT_BE_NULL;
                }
                case USER_NOT_FOUND: {
                    return USER_NOT_FOUND;
                }
                case USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_PARTECIPANTS_TO_THIS_ACTIVITY: {
                    return USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + ADD_PARTECIPANTS_TO_THIS_ACTIVITY;
                }
                case THE_LIST_OF_USERNAMES_IS_EMPTY: {
                    return THE_LIST_OF_USERNAMES_IS_EMPTY;
                }
                case NOT_EVERY_SPECIFIED_USERNAME_EXISTS: {
                    return NOT_EVERY_SPECIFIED_USERNAME_EXISTS;
                }
                default:
                    return null;
            }
        }
    }

    public String removePartecipant(UpdatePermissionsDto request) {
        try {
            Preconditions.checkNotNull(request.getActivityId(), THE_ACTIVITY_ID_CANNOT_BE_NULL);
            User user = tokenRepository.getUserByToken(request.getToken());
            Preconditions.checkNotNull(user, USER_NOT_FOUND);
            Activity activity = activityRepository.findById(request.getActivityId()).get();
            //check if the user has permissions to remove a partecipant
            Preconditions.checkArgument(activity.getAdmins().contains(user), USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_PARTECIPANTS_FROM_THIS_ACTIVITY);

            //eliminates duplicate strings from the list of usernames
            List<String> usernames = request.getUsernames().stream().distinct().toList();
            //check that every username exists
            Preconditions.checkArgument(userRepository.countByUsernames(usernames).equals(Integer.valueOf(usernames.size())),
                    NOT_EVERY_SPECIFIED_USERNAME_EXISTS);

            Iterator<User> userIterator = activity.getPartecipants().iterator();
            //removes all the specified users if they are contained in the partecipants list
            User userToAnalize;
            while (userIterator.hasNext()) {
                userToAnalize = userIterator.next();
                if (usernames.contains(userToAnalize.getUsername())) {
                    activity.getPartecipants().remove(userToAnalize);
                }
            }
            activityRepository.save(activity);
            return "";
        } catch (Exception e) {
            switch (e.getMessage()) {
                case THE_ACTIVITY_ID_CANNOT_BE_NULL: {
                    return THE_ACTIVITY_ID_CANNOT_BE_NULL;
                }
                case USER_NOT_FOUND: {
                    return USER_NOT_FOUND;
                }
                case USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_PARTECIPANTS_FROM_THIS_ACTIVITY: {
                    return USER_DOES_NOT_HAVE_PERMISSIONS_TO_ + REMOVE_PARTECIPANTS_FROM_THIS_ACTIVITY;
                }
                default:
                    return null;
            }
        }
    }


}
