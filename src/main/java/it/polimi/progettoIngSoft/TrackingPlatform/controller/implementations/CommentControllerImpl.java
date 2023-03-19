package it.polimi.progettoIngSoft.TrackingPlatform.controller.implementations;

import com.google.common.base.Preconditions;
import com.vaadin.flow.component.html.Pre;
import it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces.CommentController;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.*;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.comment.Comment;
import it.polimi.progettoIngSoft.TrackingPlatform.service.CommentService;
import it.polimi.progettoIngSoft.TrackingPlatform.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@CrossOrigin(origins="*")
public class CommentControllerImpl implements CommentController {

    @Autowired
    TokenService tokenService;

    @Autowired
    CommentService commentService;


    private final String PRECONDITIONS_FAILED = "preconditions failed";
    private final String RESPONSE_NULL = "response null";

    @Override
    public ResponseEntity<CommentDto> addComment(@RequestBody AddCommentDto addComment){
        try{
            // token validation and body nullity
            Preconditions.checkArgument(addComment != null && tokenService.isUserEnabled(addComment.getToken()), PRECONDITIONS_FAILED );

            // execute service method and get response
            CommentDto  comment = commentService.addComment(addComment);

            // check valid return object
            Preconditions.checkArgument(comment != null, RESPONSE_NULL);

            // return dto response
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }catch(Exception e){
            return exceptionReturn(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<CommentDto> removeComment(@RequestBody RemoveCommentDto removeComment){
        try{
            // token validation and body nullity
            Preconditions.checkArgument(removeComment != null && tokenService.isUserEnabled(removeComment.getToken()), PRECONDITIONS_FAILED );

            // execute service method and get response
            CommentDto  comment = commentService.removeComment(removeComment);

            // check valid return object
            Preconditions.checkArgument(comment != null, RESPONSE_NULL);

            // return dto response
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }catch(Exception e){
            return exceptionReturn(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<List<CommentDto>> getComments(@RequestBody GetCommentsDto getComments){
        try{
            // token validation and body nullity
            Preconditions.checkArgument(getComments != null && tokenService.isUserEnabled(getComments.getToken()));

            //  execute service method and get response
            List<CommentDto> comments = commentService.getComments(getComments);

            // check response not null
            Preconditions.checkArgument(comments != null, RESPONSE_NULL);

            // return response entity
            return new ResponseEntity<>(comments, HttpStatus.OK);

        }catch(Exception e){
            e.printStackTrace();
            return exceptionReturn(e.getMessage());
        }
    }
    @Override
    public ResponseEntity<CommentReplyDto> addCommentReply(@RequestBody AddCommentReplyDto commentReply){
        try{

            // check token and body nullity
            Preconditions.checkArgument(commentReply != null && tokenService.isUserEnabled(commentReply.getToken()), PRECONDITIONS_FAILED);

            // call service method and get response
            CommentReplyDto reply = commentService.addCommentReply(commentReply);

            // check response not null
            Preconditions.checkArgument(reply != null, RESPONSE_NULL);

            // return response entity
            return new ResponseEntity<>(reply,HttpStatus.OK);
        }catch(Exception e){
            return exceptionReturn(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<CommentReplyDto> removeCommentReply(@RequestBody RemoveCommentReplyDto removeReply){
        try{
            // token validation and body nullity
            Preconditions.checkArgument(removeReply != null && tokenService.isUserEnabled(removeReply.getToken()),PRECONDITIONS_FAILED);
            // execute service method and get response
            CommentReplyDto reply = commentService.removeCommentReply(removeReply);

            // check response
            Preconditions.checkArgument(reply != null, RESPONSE_NULL );

            // return response entity
            return new ResponseEntity<>(reply,HttpStatus.OK);
        }catch(Exception e){
            return exceptionReturn(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<List<CommentReplyDto>> getRepliesFromComment(@RequestBody GetCommentRepliesDto getReplies){
        try{
            // token validation and check body nullity
            Preconditions.checkArgument(getReplies != null && tokenService.isUserEnabled(getReplies.getToken()), PRECONDITIONS_FAILED);
            // call service method and get response
            List<CommentReplyDto> replies = commentService.getCommentReplies(getReplies);

            // check response not null
            Preconditions.checkArgument(replies != null, RESPONSE_NULL);

            // return response entity
            return new ResponseEntity<>(replies,HttpStatus.OK);
        }catch(Exception e){
            return exceptionReturn(e.getMessage());
        }
    }
    private ResponseEntity exceptionReturn(String errorMessage) {
        switch (errorMessage) {
            case PRECONDITIONS_FAILED : {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            case RESPONSE_NULL : {
                return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
            }
            default: return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
