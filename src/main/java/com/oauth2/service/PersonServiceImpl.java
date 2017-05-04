package com.oauth2.service;

import com.oauth2.entity.Person;
import com.oauth2.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author waylon on 02/05/2017.
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Set<Person> retrieveAll() {
        return new HashSet<>(personRepository.findAll());
    }

    @Override
    public Person findById(Long id) {
        return personRepository.findOne(id);
    }

    @Override
    public boolean exists(Person person) {
        return personRepository.findByIdCard(person.getIdCard()) != null;
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void update(Person currentPerson, Person person) {

        currentPerson.setName(person.getName());
        currentPerson.setIdCard(person.getIdCard());
        currentPerson.setSurname(person.getSurname());

        personRepository.save(currentPerson);
    }

    @Override
    public void delete(Person person) {
        personRepository.delete(person);
    }

    @Override
    public void deleteAll() {
        personRepository.deleteAll();
    }
}
