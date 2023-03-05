package it.polimi.progettoIngSoft.TrackingPlatform.service;

import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.AddCommentReplyDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.CommentReplyDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.comment.Comment;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Post;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.PostRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.TokenRepository;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentReplyService {

    @Autowired
    private TokenRepository tokenRepository;



    @Autowired
    private UserRepository userRepository;

    private CommentReplyDto addCommentReply(AddCommentReplyDto addComment){
        try{
            // get user from token
            User u = tokenRepository.getUserByToken(addComment.getToken());

            // get post from id post
            return  null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
