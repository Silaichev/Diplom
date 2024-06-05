package com.diplom.repo.question;

import com.diplom.models.questions.TestQuestion;
import org.springframework.data.repository.CrudRepository;

public interface TestQuestionRepo extends CrudRepository<TestQuestion,Long> {
}
