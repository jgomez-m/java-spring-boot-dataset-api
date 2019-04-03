package com.hackerrank.github.service.impl;

import com.hackerrank.github.model.Actor;
import com.hackerrank.github.model.Event;
import com.hackerrank.github.repository.ActorRepository;
import com.hackerrank.github.repository.EventRepository;
import com.hackerrank.github.service.ActorService;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorServiceImpl implements ActorService
{

    private ActorRepository actorRepository;
    private EventRepository eventRepository;

    @Autowired
    public ActorServiceImpl(EventRepository eventRepository, ActorRepository actorRepository)
    {
        this.eventRepository = eventRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public boolean exist(final Actor actor)
    {
        return actorRepository.exists(actor.getId());
    }

    @Override
    public boolean updateAvatar(final Actor actor)
    {
        Actor oldActor = actorRepository.findOne(actor.getId());
        if(!oldActor.getLogin().equals(actor.getLogin())){
            return false;
        }
        oldActor.setAvatar(actor.getAvatar());
        actorRepository.save(oldActor);
        return true;
    }

    @Override
    public List<Actor> findAllActors()
    {
        List<Event> events = (List<Event>) eventRepository.findAll();
        events = events.stream()
            .sorted(Comparator.comparing(e -> e.getCreatedAt()))
            .collect(Collectors.toList());
        Map<Actor, Integer> mapActors = new HashMap<>();
        events.forEach(x -> {
            if (mapActors.containsKey(x.getActor()))
            {
                int numberOfEvents = mapActors.get(x.getActor());
                mapActors.put(x.getActor(), numberOfEvents + 1);
            }
            else
            {
                mapActors.put(x.getActor(), 1);
            }
        });


        return mapActors.entrySet().stream()
            .sorted(Comparator.comparing(Map.Entry::getValue))
            .sorted(Comparator.comparing(a -> a.getKey().getLogin()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    @Override
    public List<Actor> findActorsByStreak()
    {
        List<Event> events = (List<Event>) eventRepository.findAll();
        events.sort(Comparator.comparing(Event::getId)
            .thenComparing(Comparator.comparing(Event::getCreatedAt).reversed()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date previousDate = events.get(0).getCreatedAt();
        Date currentDate = null;
        Actor previousActor = events.get(0).getActor();
        Map<Actor, Integer> mapActors = new HashMap<>();

        for(Event e: events){
            currentDate = e.getCreatedAt();
            long daysBetween = TimeUnit.DAYS.convert(currentDate.getTime() - previousDate.getTime(),
                TimeUnit.MILLISECONDS);
            daysBetween = Math.abs(daysBetween);
            if(daysBetween <= 1 && e.getActor().equals(previousActor)){
                addToActorMap(e, mapActors);
            }
            else if(daysBetween > 1 || !mapActors.containsKey(e.getActor())){
                if(!mapActors.containsKey(e.getActor())){
                    mapActors.put(e.getActor(), 0);
                }
            }

            previousActor = e.getActor();
            previousDate = e.getCreatedAt();

        }

        return mapActors.entrySet().stream()
            .sorted(Comparator.comparing(Map.Entry::getValue))
            .sorted(Comparator.comparing(a -> a.getKey().getLogin()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    private void addToActorMap(Event e, Map<Actor, Integer> mapActors){

        if(mapActors.containsKey(e.getActor())){
            int value = mapActors.get(e.getActor());
            mapActors.put(e.getActor(), value + 1);
        }
        else{
            mapActors.put(e.getActor(), 0);
        }

    }
}
