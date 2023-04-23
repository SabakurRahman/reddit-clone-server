package com.sabakurreddit.reddit.service;

import com.sabakurreddit.reddit.dto.VoteResponse;
import com.sabakurreddit.reddit.exceptions.PostNotFoundException;
import com.sabakurreddit.reddit.exceptions.SpringRedditException;
import com.sabakurreddit.reddit.model.Post;
import com.sabakurreddit.reddit.model.Vote;
import com.sabakurreddit.reddit.repository.PostRepository;
import com.sabakurreddit.reddit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sabakurreddit.reddit.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
@Builder
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    public void votePost(VoteResponse voteResponse) {
        Post post = postRepository.findById(voteResponse.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteResponse.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteResponse.getVoteType())) {
            throw new SpringRedditException("You have already "
                    + voteResponse.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteResponse.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteResponse, post));
        postRepository.save(post);

    }
    private Vote mapToVote(VoteResponse voteResponse, Post post) {
        return Vote.builder()
                .voteType(voteResponse.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
