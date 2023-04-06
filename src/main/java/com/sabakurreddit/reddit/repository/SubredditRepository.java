package com.sabakurreddit.reddit.repository;

import com.sabakurreddit.reddit.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepository extends JpaRepository<Long, Subreddit> {
}
