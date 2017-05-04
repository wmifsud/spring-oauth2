package com.oauth2.repository;

import com.oauth2.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author waylon on 02/05/2017.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByIdCard(String idCard);
}
