package com.diplom.repo.question;

import com.diplom.models.questions.OpenQuestion;
import org.springframework.data.repository.CrudRepository;

public interface OpenQuestionRepo extends CrudRepository<OpenQuestion,Long> {
}
