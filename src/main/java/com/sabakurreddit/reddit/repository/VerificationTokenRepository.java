package com.sabakurreddit.reddit.repository;

import com.sabakurreddit.reddit.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<Long, VerificationToken> {

}
