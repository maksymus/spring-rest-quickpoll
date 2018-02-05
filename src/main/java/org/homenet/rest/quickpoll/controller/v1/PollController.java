package org.homenet.rest.quickpoll.controller.v1;

import org.homenet.rest.quickpoll.controller.error.ResourceNotFoundException;
import org.homenet.rest.quickpoll.domain.Poll;
import org.homenet.rest.quickpoll.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController("PollControllerV1")
@RequestMapping("/api/v1")
public class PollController {

    @Autowired
    private PollRepository pollRepository;

    @RequestMapping(value = "/polls", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Poll>> getPolls() {
        List<Poll> polls = pollRepository.findAll();
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
