package it.polimi.progettoIngSoft.TrackingPlatform.service;

import com.google.common.base.Preconditions;
import it.polimi.progettoIngSoft.TrackingPlatform.model.DTO.*;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.comment.Comment;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.comment.CommentReply;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.post.Post;
import it.polimi.progettoIngSoft.TrackingPlatform.model.entities.user.User;
import it.polimi.progettoIngSoft.TrackingPlatform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private CommentReplyRepository commentReplyRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;


    public CommentDto addComment(AddCommentDto addComment){
        try{
            // get user from token
            User commentCreator = tokenRepository.getUserByToken(addComment.getToken());

            // get post from id
            Post post = postRepository.getPostById(addComment.getId_post());
            Preconditions.checkArgument(commentCreator != null && post != null,"riferimenti inesistenti");

            // creation of new comment
            Comment comment = new Comment();
            comment.setCommentCreator(commentCreator);
            comment.setCreationTime(Instant.now());
            comment.setText(addComment.getContent());
            comment.setPost(post);

            // insert new comment
            Comment returnObject = commentRepository.save(comment);

            // check response not null
            Preconditions.checkArgument(returnObject != null, "response null");
            // return dto
            return new CommentDto(returnObject);
        }catch (Exception e){
            return null;
        }
    }

    public CommentDto removeComment(RemoveCommentDto removeComment){
        try{
            // get user ref
            User creator = tokenRepository.getUserByToken(removeComment.getToken());

            // get comment ref
            Comment comment = commentRepository.findById(removeComment.getIdComment()).get();

            // check references not null
            Preconditions.checkArgument(creator != null && comment != null, "riferimenti inesistenti");

            // check that the request of deleting comes from the creator of the comment
            Preconditions.checkArgument(comment.getCommentCreator().equals(creator), "only comment owner can delete");

            // virtual delete of comment
            comment.setDeleted(true);

            // delete the comment
            Comment returnComment = commentRepository.save(comment);

            return new CommentDto(returnComment);
        }catch (Exception e){
            return null;
        }
    }


    public List<CommentDto> getComments(GetCommentsDto getComments){
        try{
            // get post from id
            Post post = postRepository.getPostById(getComments.getPostId());

            // check post reference
            Preconditions.checkArgument(post != null,"null references");

            // get comments from post
            List<Comment> comments = commentRepository.getCommentsByPostId(getComments.getPostId());

            // check response not null
            Preconditions.checkArgument(comments != null, "query broke");
            // create dto list
            List<CommentDto> returnList = new ArrayList<CommentDto>();

            for (Comment comment: comments){
                returnList.add(new CommentDto(comment));
            }

            // return dto object
            return  returnList;
        }catch (Exception e){
            return null;
        }
    }

    public CommentReplyDto addCommentReply(AddCommentReplyDto addComment){
        try{
            // get user from token
            User replyCreator = tokenRepository.getUserByToken(addComment.getToken());

            // get comment from id
            Comment comment = commentRepository.findById(addComment.getId_comment()).get();

            // check references not null
            Preconditions.checkArgument(replyCreator != null && comment != null, "references null");

            // create new reply
            CommentReply reply = new CommentReply();

            reply.setCreationTime(Instant.now());
            reply.setText(addComment.getContent());
            reply.setUser(replyCreator);
            reply.setComment(comment);
            reply.setVisible(true);

            // insert new reply
            CommentReply returnObject = commentReplyRepository.save(reply);

            // check response not null
            Preconditions.checkArgument(returnObject != null, "response null");

            // return dto
            return new CommentReplyDto(returnObject);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public CommentReplyDto removeCommentReply(RemoveCommentReplyDto removeComment) {
        try{
            // get user from token
            User replyCreator = tokenRepository.getUserByToken(removeComment.getToken());
            // get commentReply by id
            CommentReply commentReply = commentReplyRepository.findById(removeComment.getId_comment_reply()).get();

            // check references not null
            Preconditions.checkArgument(commentReply != null && replyCreator != null && commentReply.getCommentReplyCreator().equals(replyCreator), "only th creator can delete reply");

            // virtual delete
            commentReply.setDeleted(true);

            // commit changes
            CommentReply returnReply = commentReplyRepository.save(commentReply);

            return new CommentReplyDto(returnReply);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<CommentReplyDto> getCommentReplies(GetCommentRepliesDto getReplies){
        try{
            // get comment reference
            Comment comment = commentRepository.findById(getReplies.getId_comment()).get();

            // check reference not null
            Preconditions.checkArgument(comment != null, "base comment non existent");

            // get all replies
            List<CommentReply> replies = commentReplyRepository.getRepliesFromCommentId(comment.getId());

            Preconditions.checkArgument(replies != null, "error executing query");
            // create list of dto
            List<CommentReplyDto> repliesDto = new ArrayList<>();
            for (CommentReply reply : replies){
                repliesDto.add(new CommentReplyDto(reply));
            }

            return repliesDto;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
