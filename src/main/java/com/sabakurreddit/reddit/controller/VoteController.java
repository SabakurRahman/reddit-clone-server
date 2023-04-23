package com.sabakurreddit.reddit.controller;



import com.sabakurreddit.reddit.dto.VoteResponse;
import com.sabakurreddit.reddit.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vote")
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;
    @PostMapping("/votePost")
    public ResponseEntity<Void> vote(@RequestBody VoteResponse votResponse){

       voteService.votePost(votResponse);

        return  new ResponseEntity<>(HttpStatus.OK);
    }
}
