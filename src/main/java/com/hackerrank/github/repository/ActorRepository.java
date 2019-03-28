package com.hackerrank.github.repository;

import com.hackerrank.github.model.Actor;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<Actor, Long>
{
    //List<Actor> findAllOrderedByTotalEvents();
}
