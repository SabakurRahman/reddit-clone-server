package com.sabakurreddit.reddit.repository;

import com.sabakurreddit.reddit.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Long, Post> {
}
