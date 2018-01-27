package org.homenet.rest.quickpoll.repository;

import org.homenet.rest.quickpoll.domain.Poll;
import org.homenet.rest.quickpoll.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByOptionPollId(long pollId);
}
