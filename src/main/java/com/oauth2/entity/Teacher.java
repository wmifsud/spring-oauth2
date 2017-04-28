package com.oauth2.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * @author waylon on 27/04/2017.
 */
@Entity
@Table(name = "teacher", schema = "users")
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
}
