package com.course.demo.entities.jpaentities;

import com.course.demo.entities.Rank;
import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "officers")
public  class Officer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Enumerated(EnumType.STRING)
    private Rank rank;
    private  String firstName;
    private  String lastName;

    public Officer(){}

    public Officer(Integer id,
                   Rank rank,
                   String firstName,
                   String lastName) {
        this.id = id;
        this.rank = rank;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public Rank getRank() {
        return rank;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Officer officer = (Officer) o;
        return Objects.equals(id, officer.id) && rank == officer.rank && Objects.equals(firstName, officer.firstName) && Objects.equals(lastName, officer.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rank, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Officer[" +
                "id=" + id + ", " +
                "rank=" + rank + ", " +
                "firstName=" + firstName + ", " +
                "lastName=" + lastName + ']';
    }

}