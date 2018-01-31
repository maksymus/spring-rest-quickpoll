package org.homenet.rest.quickpoll.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.homenet.rest.quickpoll.controller.dto.OptionCount;
import org.homenet.rest.quickpoll.controller.dto.VoteResult;
import org.homenet.rest.quickpoll.domain.Option;
import org.homenet.rest.quickpoll.domain.Vote;
import org.homenet.rest.quickpoll.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("Computes vote result")
@RestController
public class ComputeResultController {
    @Autowired
    private VoteRepository voteRepository;

    @ApiOperation(value = "Retrieves all polls", response = VoteResult.class)
    @RequestMapping(value = "/api/computeresult", method = RequestMethod.GET)
    public ResponseEntity<VoteResult> computeResult(@RequestParam Long pollId) {
        VoteResult voteResult = countVotes(pollId);
        return ResponseEntity.ok(voteResult);
    }

    private VoteResult countVotes(Long pollId) {
        VoteResult voteResult = new VoteResult();
        List<Vote> allVotes = voteRepository.findByOptionPollId(pollId);

        // <optionId, count>
        Map<Long, Long> options = new HashMap<>();

        for (Vote vote : allVotes) {
            options.merge(vote.getOption().getId(), 1l, (a, b) -> a++);
        }

        int totalCount = 0;
        for (Long optionId : options.keySet()) {
            long count = options.get(optionId);
            totalCount += count;

            voteResult.optionCounts.add(new OptionCount(optionId, count));
        }

        voteResult.totalCount = totalCount;

        return voteResult;
    }
}
