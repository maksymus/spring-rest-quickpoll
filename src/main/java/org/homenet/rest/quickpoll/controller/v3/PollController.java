package org.homenet.rest.quickpoll.controller.v3;

import org.homenet.rest.quickpoll.controller.error.ResourceNotFoundException;
import org.homenet.rest.quickpoll.domain.Poll;
import org.homenet.rest.quickpoll.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController("PollControllerV3")
@RequestMapping({"/api/v3", "/api/oauth2/v3" })
public class PollController {

    @Autowired
    private PollRepository pollRepository;

    // localhost:8080/api/v2/polls?page=0&size=2
    // localhost:8080/api/v2/polls?sort=question,desc
    @RequestMapping(value = "/polls", method = RequestMethod.GET)
    public ResponseEntity<Page<Poll>> getPolls(Pageable pageable) {
        Page<Poll> polls = pollRepository.findAll(pageable);
        return ResponseEntity.ok().body(polls);
    }

    @RequestMapping(value = "/polls", method = RequestMethod.POST)
    public ResponseEntity<Poll> savePoll(@Valid @RequestBody Poll poll) {
        Poll saved = pollRepository.save(poll);

        URI savedPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(poll.getId()).toUri();

        return ResponseEntity.ok().location(savedPollUri).body(saved);
    }

    @RequestMapping(value = "/polls/{id}", method = RequestMethod.GET)
    public ResponseEntity<Poll> getPoll(@PathVariable long id) {
        validatePollId(id);
        Poll poll = pollRepository.findOne(id);
        return ResponseEntity.ok().body(poll);
    }

    @RequestMapping(value = "/polls/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Poll> updatePoll(@RequestBody Poll poll, @PathVariable long id) {
        validatePollId(id);
        pollRepository.save(poll);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/polls/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Poll> deletePoll(@PathVariable long id) {
        validatePollId(id);
        pollRepository.delete(id);
        return ResponseEntity.ok().build();
    }

    private void validatePollId(long id) {
        Poll poll = pollRepository.findOne(id);
        if (poll == null)
            throw new ResourceNotFoundException(String.format("Poll not found with id = %d", id));
    }
}
