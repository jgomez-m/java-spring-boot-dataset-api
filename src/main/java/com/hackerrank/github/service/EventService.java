package com.hackerrank.github.service;

import com.hackerrank.github.model.Event;
import java.util.List;

public interface EventService
{
    void deleteAll();

    boolean add(Event event);

    List<Event> findAll();

    List<Event> getEventByActorId(Long id);
}
