package com.uball.uballapp.models;
import com.uball.uballapp.repos.UserRepository;
import sun.util.calendar.BaseCalendar;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date DOB;

    @Column(nullable = false)
    private char gender;

    private boolean isAdmin;

    private long points;

    @OneToOne
    private League league;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Score> scores;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="users_groups",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="group_id")}
    )
    private List<Group> groups;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, Date DOB, char gender, boolean isAdmin, long points) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.DOB = DOB;
        this.gender = gender;
        this.isAdmin = isAdmin;
        this.points = points;
    }

    public User(User copy) {
        firstName = copy.firstName;
        lastName = copy.lastName;
        email = copy.email;
        password = copy.password;
        DOB = copy.DOB;
        gender = copy.gender;
        isAdmin = copy.isAdmin;
        points = copy.points;

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
