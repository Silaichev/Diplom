package com.diplom.models.questions;

import com.diplom.models.Test;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "test_questions")
public class TestQuestion {

    @ManyToOne
    private Test test;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ElementCollection(fetch = FetchType.EAGER)
    public List<String> variants = new ArrayList<>();
    public String answer;
    public String question;

    public TestQuestion() {
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

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }

    public void addVariant(String variant){
        Object o = Objects.isNull(this.variants) || this.variants.isEmpty() ? this.variants = Arrays.asList(variant) : this.variants.add(variant);
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
