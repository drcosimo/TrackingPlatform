package it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces;

import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.AddCommentReplyDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.CommentReplyDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.GetCommentRepliesDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.RemoveCommentReplyDto;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.comment.CommentReply;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "/replies/", produces="application/json" , consumes="application/json")
public interface CommentReplyController {

    @PostMapping("addReply/")
    public ResponseEntity<CommentReplyDto> addCommentReply(@RequestBody AddCommentReplyDto commentReply);

    @PostMapping("removeReply/")
    public ResponseEntity<CommentReplyDto> removeCommentReply(@RequestBody RemoveCommentReplyDto commentReply);

    @PostMapping("getReplies/")
    public ResponseEntity<List<CommentReplyDto>> getRepliesFromComment(@RequestBody GetCommentRepliesDto getReplies);
}
