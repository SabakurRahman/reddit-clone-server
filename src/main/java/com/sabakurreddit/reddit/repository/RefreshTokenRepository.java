package com.sabakurreddit.reddit.repository;

import com.sabakurreddit.reddit.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<Long, RefreshToken> {
}
