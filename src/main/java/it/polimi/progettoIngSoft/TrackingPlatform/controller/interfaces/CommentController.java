package it.polimi.progettoIngSoft.TrackingPlatform.controller.interfaces;

import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "/comments", produces="application/json" , consumes="application/json")
public interface CommentController {

    @PostMapping("/addComment")
    public ResponseEntity<CommentDto> addComment(@RequestBody AddCommentDto addComment);

    @PostMapping("/removeComment")
    public ResponseEntity<CommentDto> removeComment(@RequestBody RemoveCommentDto removeComment);

    @PostMapping("/getComments")
    public ResponseEntity<List<CommentDto>> getComments(@RequestBody GetCommentsDto getComments);
    @PostMapping("/addReply")
    public ResponseEntity<CommentReplyDto> addCommentReply(@RequestBody AddCommentReplyDto commentReply);

    @PostMapping("/removeReply")
    public ResponseEntity<CommentReplyDto> removeCommentReply(@RequestBody RemoveCommentReplyDto commentReply);

    @PostMapping("/getReplies")
    public ResponseEntity<List<CommentReplyDto>> getRepliesFromComment(@RequestBody GetCommentRepliesDto getReplies);
}
