package com.uball.uballapp.models;

import javax.persistence.*;

@Entity
@Table(name="leagues")
public class League {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    public League() {
    }

    public League(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
