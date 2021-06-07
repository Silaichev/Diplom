package com.diplom.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {

    @ManyToOne
    private Test test;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ElementCollection(fetch = FetchType.EAGER)
    public List<String> variants = new ArrayList<>();
    public String answer;
    public String question;


    public Question(Test test,String question,List<String> variants, String answer) {
        this.variants = variants;
        this.question=question;
        this.answer = answer;
        this.test = test;
    }

    public void deleteAllVariants(){
        variants=new LinkedList<String>();
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public Question() {
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public long getId() {
        return id;
    }

    public void addVariant(String var){
        variants.add(var);
    }

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


}
