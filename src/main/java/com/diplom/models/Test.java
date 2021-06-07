package com.diplom.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String name;


    @OneToMany(mappedBy = "test")
    private List<Question> questions = new ArrayList<>();



    public Test() {
    }

    public Test(String name){
        this.name=name;
    }

    public Test(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
    }




    public void preDelete(){
        questions = null;
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

    public void addQuestion(Question q){
        questions.add(q);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", questions=" + questions +
                '}';
    }
}
