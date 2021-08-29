package com.mabu.MabuWebStore.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FEEDBACK")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_feedback")
    private int feedBackId;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private boolean status;

    @Column(name = "date_create")
    private LocalDate dateCreate;

    @Column(name = "rate")
    private int rate;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User userId;

    public Feedback() {
        super();
    }

    public Feedback(int feedBackId, String note, boolean status, LocalDate dateCreate, int rate, User userId) {
        super();
        this.feedBackId = feedBackId;
        this.note = note;
        this.status = status;
        this.dateCreate = dateCreate;
        this.rate = rate;
        this.userId = userId;
    }

    public int getFeedBackId() {
        return feedBackId;
    }

    public void setFeedBackId(int feedBackId) {
        this.feedBackId = feedBackId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDate dateCreate) {
        this.dateCreate = dateCreate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

}
