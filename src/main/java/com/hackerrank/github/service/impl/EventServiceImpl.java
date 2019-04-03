package com.hackerrank.github.service.impl;

import com.hackerrank.github.model.Event;
import com.hackerrank.github.repository.ActorRepository;
import com.hackerrank.github.repository.EventRepository;
import com.hackerrank.github.service.EventService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService
{
    private EventRepository eventRepository;
    private ActorRepository actorRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ActorRepository actorRepository){
        this.eventRepository = eventRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public void deleteAll()
    {
        eventRepository.deleteAll();
    }

    @Override
    public boolean add(final Event event)
    {
        boolean exists = eventRepository.exists(event.getId());
        if(!exists){
            Event e = eventRepository.save(event);
            return Boolean.TRUE;
        }
        else {
            return Boolean.FALSE;
        }
    }

    @Override
    public List<Event> findAll()
    {
        return (List<Event>) eventRepository.findAll();
    }

    @Override
    public List<Event> getEventByActorId(final Long id)
    {
        return eventRepository.findByActorIdOrderedByActorIdAsc(id);
    }
}
