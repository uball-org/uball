package com.uball.uballapp.models;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="scores")
public class Score {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private long score;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn (name = "machine_id")
    private Machine machine;

    public Score() {
    }

    public Score(long score, Date date) {
        this.score = score;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
