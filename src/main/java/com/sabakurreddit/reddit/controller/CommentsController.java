package com.sabakurreddit.reddit.controller;

import com.sabakurreddit.reddit.dto.CommentRequest;
import com.sabakurreddit.reddit.dto.CommentsResponse;
import com.sabakurreddit.reddit.exceptions.PostNotFoundException;
import com.sabakurreddit.reddit.model.Post;
import com.sabakurreddit.reddit.repository.CommentRepository;
import com.sabakurreddit.reddit.repository.PostRepository;
import com.sabakurreddit.reddit.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;


    @PostMapping("/postcomments")
    public ResponseEntity<Void> createComments(@RequestBody CommentRequest commentRequest){

        commentsService.createComments(commentRequest);
        return new ResponseEntity<>(CREATED);


    }

    @GetMapping("/postid/{id}")
    public List<CommentsResponse> getAllCommentsForPost(@PathVariable Long id) {

        List<CommentsResponse> commentsResponse=commentsService.getCommentByPost(id);
         return commentsResponse;


    }

    @GetMapping("/username/{userName}")
    public List<CommentsResponse> getAllCommentsByUser(@PathVariable("userName") String userName) {
        List<CommentsResponse> commentsResponse=commentsService.getCommentByUserName(userName);
        return commentsResponse;
    }




}
