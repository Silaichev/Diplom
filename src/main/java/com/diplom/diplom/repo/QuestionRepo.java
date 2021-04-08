package com.diplom.diplom.repo;

import com.diplom.diplom.models.Question;
import com.diplom.diplom.models.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepo extends CrudRepository<Question,Long> {

    Question findByQuestion(String question);
}
