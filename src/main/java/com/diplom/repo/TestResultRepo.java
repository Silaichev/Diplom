package com.diplom.repo;

import com.diplom.models.TestResult;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestResultRepo extends CrudRepository<TestResult,Long> {
    public List<TestResult> findTestResultsByTest(String test);
}
