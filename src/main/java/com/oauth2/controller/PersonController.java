package com.oauth2.controller;

import com.oauth2.entity.Person;
import com.oauth2.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    //-------------------Retrieve All Persons--------------------------------------------------------

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Set<Person>> retrievePersons() {
        Set<Person> persons = personService.retrieveAll();

        if (persons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            //You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    //-------------------Retrieve Single Person--------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {
        System.out.println("Fetching Person with id " + id);
        Person person = personService.findById(id);
        if (person == null) {
            System.out.println("Person with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person, HttpStatus.OK);
    }


    //-------------------Create a Person--------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Void> createPerson(@RequestBody Person person, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Person " + person.getName());

        if (personService.exists(person)) {
            System.out.println("A Person with id card " + person.getIdCard() + " already exists");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        personService.save(person);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/person/{id}").buildAndExpand(person.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a Person --------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Person> updatePerson(@PathVariable("id") long id, @RequestBody Person person) {
        System.out.println("Updating Person " + id);

        Person currentPerson = personService.findById(id);

        if (currentPerson == null) {
            System.out.println("Person with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        personService.update(currentPerson, person);
        return new ResponseEntity<>(currentPerson, HttpStatus.OK);
    }

    //------------------- Delete a Person --------------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Person> deletePerson(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Person with id " + id);

        Person person = personService.findById(id);
        if (person == null) {
            System.out.println("Unable to delete. Person with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        personService.delete(person);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All Persons --------------------------------------------------------

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<Person> deleteAllPersons() {
        System.out.println("Deleting All Persons");

        personService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}