package com.oauth2.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author waylon on 27/04/2017.
 */
@Entity
@Table(name = "person", schema = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    @Id
    @GeneratedValue(generator = "personGenerator")
    @GenericGenerator
    (
        name = "personGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = @Parameter(name = "sequence_name", value = "users.person_id_seq")
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "id_card")
    private String idCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
