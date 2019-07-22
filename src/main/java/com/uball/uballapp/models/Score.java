package com.uball.uballapp.models;


import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate addedscoredate;

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

    public Score(long score, LocalDate addedscoredate) {
        this.score = score;
        this.addedscoredate = addedscoredate;
    }

    public Score(Long id, long score, Date date, LocalDate addedscoredate, User user, Machine machine) {
        this.id = id;
        this.score = score;
        this.date = date;
        this.addedscoredate = addedscoredate;
        this.user = user;
        this.machine = machine;
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

    public LocalDate getAddedscoredate() {
        return addedscoredate;
    }

    public void setAddedscoredate(LocalDate addedscoredate) {
        this.addedscoredate = addedscoredate;
    }
}
