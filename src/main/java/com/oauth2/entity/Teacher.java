package com.oauth2.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author waylon on 27/04/2017.
 */
@Entity
@Table(name = "teacher", schema = "users")
@JsonTypeName("teacher")
@PrimaryKeyJoinColumn(name = "person_id", referencedColumnName = "id")
public class Teacher extends Person {

    @Column(name = "subject")
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public PersonType getPersonType() {
        return PersonType.TEACHER;
    }
}
