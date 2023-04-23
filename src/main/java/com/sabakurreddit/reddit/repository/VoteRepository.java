package com.sabakurreddit.reddit.repository;

import com.sabakurreddit.reddit.model.Post;
import com.sabakurreddit.reddit.model.User;
import com.sabakurreddit.reddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {
//
//    SELECT * FROM vote_table
//    WHERE post_id = :postId AND user_id = :userId
//    ORDER BY vote_id DESC
//    LIMIT 1;
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
