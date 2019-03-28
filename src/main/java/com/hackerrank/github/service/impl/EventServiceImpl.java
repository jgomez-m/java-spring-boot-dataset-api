package com.hackerrank.github.service.impl;

import com.hackerrank.github.model.Event;
import com.hackerrank.github.repository.EventRepository;
import com.hackerrank.github.service.EventService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService
{
    private EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository){
        this.eventRepository = eventRepository;
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
            eventRepository.save(event);
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
        return eventRepository.findByActorIdOrderedByActorIdDesc(id);
    }
}
