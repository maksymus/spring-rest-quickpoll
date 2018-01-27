package org.homenet.rest.quickpoll.repository;

import org.homenet.rest.quickpoll.domain.Option;
import org.homenet.rest.quickpoll.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
}
