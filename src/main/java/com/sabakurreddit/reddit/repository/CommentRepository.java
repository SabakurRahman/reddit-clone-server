package com.sabakurreddit.reddit.repository;

import com.sabakurreddit.reddit.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Long, Comment> {
}
