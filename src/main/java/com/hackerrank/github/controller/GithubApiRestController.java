package com.hackerrank.github.controller;

import com.hackerrank.github.model.Event;
import com.hackerrank.github.service.ActorService;
import com.hackerrank.github.service.EventService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GithubApiRestController {

    private final ActorService actorService;
    private final EventService eventService;

    @Autowired
    public GithubApiRestController(ActorService actorService, EventService eventService){
        this.actorService = actorService;
        this.eventService = eventService;
    }

    @RequestMapping(value = "/events", method = RequestMethod.DELETE)
    public ResponseEntity deleteAllEvents(){
        eventService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/events", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity newEvent(@RequestBody Event event){
        boolean eventAdded = eventService.add(event);
        if(eventAdded){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllEvents(Event event){
        List<Event> allEvents = eventService.findAll();
        return ResponseEntity.ok(allEvents);
    }

    @RequestMapping(value = "/events/actors/{actorID}", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getEventsByActorId(@PathVariable("actorID") Long actorId){
        List<Event> eventsByActor = eventService.getEventByActorId(actorId);
        if(eventsByActor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(eventsByActor);
    }

}
