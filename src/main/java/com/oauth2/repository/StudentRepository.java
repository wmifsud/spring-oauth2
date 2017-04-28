package com.oauth2.repository;

import com.oauth2.entity.Person;
import com.oauth2.entity.Student;
import com.oauth2.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author waylon on 21/03/2017.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

}
