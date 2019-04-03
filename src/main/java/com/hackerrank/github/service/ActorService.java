package com.hackerrank.github.service;

import com.hackerrank.github.model.Actor;
import java.util.List;

public interface ActorService
{
    boolean exist(Actor actor);

    boolean updateAvatar(Actor actor);

    List<Actor> findAllActors();

    List<Actor> findActorsByStreak();
}
