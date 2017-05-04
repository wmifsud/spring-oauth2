package com.oauth2.service;

import com.oauth2.entity.Person;

import java.util.Set;

/**
 * @author waylon on 02/05/2017.
 */
public interface PersonService {

    Set<Person> retrieveAll();

    Person findById(Long id);

    boolean exists(Person person);

    Person save(Person person);

    void update(Person currentPerson, Person person);

    void delete(Person person);

    void deleteAll();
}
