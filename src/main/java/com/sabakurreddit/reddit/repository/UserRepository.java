package com.sabakurreddit.reddit.repository;

import com.sabakurreddit.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


     Optional<User> findByUsername(String username);

//      @Query(value = "SELECT enabled FROM `user` WHERE enabled=1 AND enabled !=0", nativeQuery = true)
//      boolean CheckUserEnableOrNot();
     //   User findByEmailId(String email);
//@Query(value = "SELECT * FROM `user` WHERE username=?1 or email=?1", nativeQuery = true)
//     User findByUsername(String username,String email);
}
