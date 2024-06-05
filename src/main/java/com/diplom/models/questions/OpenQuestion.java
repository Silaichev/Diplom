package com.diplom.models.questions;

import com.diplom.models.Test;

import javax.persistence.*;

@Entity
@Table(name = "open_questions")
public class OpenQuestion{

        @ManyToOne
        private Test test;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        private String question;

        public OpenQuestion() {
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

        public String getQuestion() {
                return question;
        }

        public void setQuestion(String question) {
                this.question = question;
        }
}
