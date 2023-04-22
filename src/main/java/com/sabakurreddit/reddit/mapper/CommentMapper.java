package com.sabakurreddit.reddit.mapper;

import com.sabakurreddit.reddit.dto.CommentRequest;
import com.sabakurreddit.reddit.model.Comment;
import com.sabakurreddit.reddit.model.Post;
import com.sabakurreddit.reddit.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentRequest.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    Comment map(CommentRequest commentRequest, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentRequest mapToDto(Comment comment);
}
