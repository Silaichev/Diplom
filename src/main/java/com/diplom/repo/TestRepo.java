package com.diplom.repo;

import com.diplom.models.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestRepo extends CrudRepository<Test,Long> {

    List<Test> findAllByName(String name);

    Test findByName(String name);

    Test findById(long id);
}
