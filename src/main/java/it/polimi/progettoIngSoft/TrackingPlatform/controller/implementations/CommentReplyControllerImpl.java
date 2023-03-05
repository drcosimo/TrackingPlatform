package it.polimi.progettoIngSoft.TrackingPlatform.controller.implementations;

import com.google.common.base.Preconditions;
import it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces.CommentReplyController;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.AddCommentReplyDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.CommentReplyDto;
import it.polimi.progettoIngSoft.TrackingPlatform.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

public class CommentReplyControllerImpl implements CommentReplyController {

    @Autowired
    TokenService tokenService;
    private final String PRECONDITIONS_FAILED = "preconditions failed";
    private final String RESPONSE_NULL = "response null";

    @Override
    public ResponseEntity<CommentReplyDto> addCommentReply(@RequestBody AddCommentReplyDto commentReply){
        try{

            // check token and body nullity
            Preconditions.checkArgument(commentReply != null && tokenService.isUserEnabled(commentReply.getToken()), PRECONDITIONS_FAILED);

            // call service method and get response

        }catch(Exception e){
            return exceptionReturn(e.getMessage());
        }
    };
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
