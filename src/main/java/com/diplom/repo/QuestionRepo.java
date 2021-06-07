package com.diplom.repo;

import com.diplom.models.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepo extends CrudRepository<Question,Long> {

    Question findByQuestion(String question);
}
