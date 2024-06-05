package com.diplom.sevices;

import com.diplom.models.File;
import com.diplom.repo.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FileService {

    @Autowired
    private FileRepo fileRepo;

    public List<File> getAllFiles(){
        return StreamSupport.stream(fileRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public File save(File file){
        return fileRepo.save(file);
    }
}
