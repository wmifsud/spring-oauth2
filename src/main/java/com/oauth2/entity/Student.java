package com.oauth2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author waylon on 27/04/2017.
 */
@Entity
@Table(name = "student", schema = "users")
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
}
