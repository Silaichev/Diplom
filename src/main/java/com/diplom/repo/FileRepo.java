package com.diplom.repo;


import com.diplom.models.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepo extends CrudRepository<File,Long> {
    File findByName(String name);
}
