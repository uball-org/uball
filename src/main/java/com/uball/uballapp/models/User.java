package com.uball.uballapp.models;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import com.uball.uballapp.repos.UserRepository;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank(message = "Must enter username")
    @Size(min = 3, message = "Username must be at least 3 characters.")
    @Column(nullable = false)
    private String username;

    @NotBlank(message = "Must enter first name")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Must enter last name")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "Must enter email")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Must enter password")
    @Size(min = 5, message = "Password must be at least 5 characters.")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "Must enter a birthdate")
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    public User(String username, String firstName, String lastName, String email, String password, Date DOB, char gender, boolean isAdmin, long points) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.DOB = DOB;
        this.gender = gender;
        this.isAdmin = isAdmin;
        this.points = points;
    }

    public User(String username, String firstName, String lastName, String email, String password, Date DOB, char gender, boolean isAdmin, long points, League league, List<Score> scores, List<Group> groups) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.DOB = DOB;
        this.gender = gender;
        this.isAdmin = isAdmin;
        this.points = points;
        this.league = league;
        this.scores = scores;
        this.groups = groups;
    }

    public User(User copy) {
        id = copy.id;
        username = copy.username;
        firstName = copy.firstName;
        lastName = copy.lastName;
        email = copy.email;
        password = copy.password;
        DOB = copy.DOB;
        gender = copy.gender;
        isAdmin = copy.isAdmin;
        points = copy.points;
        league = copy.league;
        scores = copy.scores;
        groups = copy.groups;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
