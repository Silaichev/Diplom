package com.diplom.models;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "test_result")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idTR;

    private int score;
    private int numOfQuestions;
    private Date date;

    private String test;

    @ManyToOne
    private User user;


    public TestResult(int score, int numOfQuestions, String test, User user) {
        this.score = score;
        this.numOfQuestions = numOfQuestions;
        this.date = new Date();
        this.test = test;
        this.user = user;
    }


    public TestResult() {
        this.date = new Date();
    }

    public long getIdTR() {
        return idTR;
    }

    public void setIdTR(long idTR) {
        this.idTR = idTR;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
