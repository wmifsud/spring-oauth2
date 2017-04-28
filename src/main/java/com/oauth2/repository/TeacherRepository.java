package com.oauth2.repository;

import com.oauth2.entity.ClientDetails;
import com.oauth2.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author waylon on 21/03/2017.
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
