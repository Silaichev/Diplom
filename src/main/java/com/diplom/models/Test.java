package com.diplom.models;


import com.diplom.models.questions.OpenQuestion;
import com.diplom.models.questions.TestQuestion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String name;


    @OneToMany(mappedBy = "test")
    private List<TestQuestion> testQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "test")
    private List<OpenQuestion> openQuestions = new ArrayList<>();

    public Test() {
    }

    public Test(String name){
        this.name=name;
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

    public List<TestQuestion> getTestQuestions() {
        return testQuestions;
    }

    public void setTestQuestions(List<TestQuestion> testQuestions) {
        this.testQuestions = testQuestions;
    }

    public void addTestQuestion(TestQuestion testQuestion){
        Object o = Objects.isNull(testQuestions) ? this.testQuestions = Arrays.asList(testQuestion) : this.testQuestions.add(testQuestion);
    }

    public List<OpenQuestion> getOpenQuestions() {
        return openQuestions;
    }

    public void setOpenQuestions(List<OpenQuestion> openQuestions) {
        this.openQuestions = openQuestions;
    }

    public void addOpenQuestion(OpenQuestion openQuestion){
        Object o = Objects.isNull(this.openQuestions) ? this.openQuestions = Arrays.asList(openQuestion) : this.openQuestions.add(openQuestion);
    }
}
