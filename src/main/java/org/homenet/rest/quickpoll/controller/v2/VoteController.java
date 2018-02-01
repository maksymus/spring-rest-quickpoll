package org.homenet.rest.quickpoll.controller.v2;

import org.homenet.rest.quickpoll.domain.Vote;
import org.homenet.rest.quickpoll.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController("VoteControllerV2")
@RequestMapping("/api/v2")
public class VoteController {
    @Autowired
    private VoteRepository voteRepository;

    @RequestMapping(value = "/polls/{pollId}/votes", method = RequestMethod.POST)
    public ResponseEntity<Vote> saveVote(@RequestBody Vote vote, @PathVariable long pollId) {
        Vote saved = voteRepository.save(vote);

        URI savedVoteUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(vote.getId()).toUri();

        return ResponseEntity.ok().location(savedVoteUri).body(saved);
    }

    @RequestMapping(value="/polls/{pollId}/votes", method=RequestMethod.GET)
    public Iterable<Vote> getAllVotes(@PathVariable Long pollId) {
        return	voteRepository.findByOptionPollId(pollId);
    }
}
