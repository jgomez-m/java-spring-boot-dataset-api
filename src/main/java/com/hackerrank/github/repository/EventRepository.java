package com.hackerrank.github.repository;

import com.hackerrank.github.model.Event;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends CrudRepository<Event, Long>
{
    @Query("SELECT e FROM Event e WHERE e.actor.id = :actorId ORDER BY e.id ASC")
    List<Event> findByActorIdOrderedByActorIdAsc(@Param("actorId") Long actorId);
}
