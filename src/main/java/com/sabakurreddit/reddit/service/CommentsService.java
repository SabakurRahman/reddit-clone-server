package com.sabakurreddit.reddit.service;

import com.sabakurreddit.reddit.dto.CommentRequest;
import com.sabakurreddit.reddit.dto.CommentsResponse;
import com.sabakurreddit.reddit.dto.PostResponse;
import com.sabakurreddit.reddit.exceptions.PostNotFoundException;
import com.sabakurreddit.reddit.exceptions.SpringRedditException;
import com.sabakurreddit.reddit.model.Comment;
import com.sabakurreddit.reddit.model.Post;
import com.sabakurreddit.reddit.model.User;
import com.sabakurreddit.reddit.repository.CommentRepository;
import com.sabakurreddit.reddit.repository.PostRepository;
import com.sabakurreddit.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentsService {

    private final CommentRepository commentRepository;
    private final AuthService authService;
    private  final PostRepository postRepository;
    private  final UserRepository userRepository;


    public Comment createComments(CommentRequest commentRequest) {

        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentRequest.getPostId().toString()));
        User user =  authService.getCurrentUser();

        if ( commentRequest == null && post == null && user == null ) {
            return null;
        }

        Comment  comment = new Comment();

        if ( commentRequest != null ) {
            comment.setText( commentRequest.getText() );
        }
        if ( post != null ) {
            comment.setPost( post );
            comment.setUser( post.getUser() );
        }
        comment.setCreatedDate( java.time.Instant.now() );

       return commentRepository.save(comment);




    }

    public List<CommentsResponse> getCommentByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));

        List<Comment> comments = commentRepository.findByPost(post);

        List<CommentsResponse> commentArrayList = new ArrayList<>();
        for (Comment comment : comments) {
            CommentsResponse commentsResponse = new CommentsResponse();
            commentsResponse.setId(comment.getId());
            commentsResponse.setCreatedDate(comment.getCreatedDate());
            commentsResponse.setText(comment.getText());
            commentsResponse.setUserName(comment.getUser().getUsername());
            commentArrayList.add(commentsResponse);
        }

        return commentArrayList;
    }

    public List<CommentsResponse> getCommentByUserName(String userName) {

        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new SpringRedditException("User not found with name - " + userName));

        List<Comment> comments = commentRepository.findAllByUser(user);
        List<CommentsResponse> commentArrayList = new ArrayList<>();
        for (Comment comment : comments) {
            CommentsResponse commentsResponse = new CommentsResponse();
            commentsResponse.setId(comment.getId());
            commentsResponse.setCreatedDate(comment.getCreatedDate());
            commentsResponse.setText(comment.getText());
            commentsResponse.setUserName(comment.getUser().getUsername());
            commentArrayList.add(commentsResponse);
        }
        return commentArrayList;
    }
}
