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
@Table(name = "student", schema = "users")
@JsonTypeName("student")
@PrimaryKeyJoinColumn(name = "person_id", referencedColumnName = "id")
public class Student extends Person {

    @Column(name = "current_year")
    private Integer currentYear;

    @Column(name = "lessons_per_week")
    private Integer lessonsPerWeek;

    public Integer getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Integer currentYear) {
        this.currentYear = currentYear;
    }

    public Integer getLessonsPerWeek() {
        return lessonsPerWeek;
    }

    public void setLessonsPerWeek(Integer lessonsPerWeek) {
        this.lessonsPerWeek = lessonsPerWeek;
    }

    @Override
    public PersonType getPersonType() {
        return PersonType.STUDENT;
    }

    @Override
    public String toString() {
        return "Student{" +
                "currentYear=" + currentYear +
                ", lessonsPerWeek=" + lessonsPerWeek +
                ", id=" + id +
                ", personType=" + getPersonType() +
                ", name='" + name + '\'' +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", idCard='" + getIdCard() + '\'' +
                '}';
    }
}
