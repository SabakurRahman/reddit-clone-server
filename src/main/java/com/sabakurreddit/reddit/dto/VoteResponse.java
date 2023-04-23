package com.sabakurreddit.reddit.dto;

import com.sabakurreddit.reddit.model.VoteType;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteResponse {
    private VoteType voteType;
    private Long postId;
}
