package com.diplom.util;

import com.diplom.sevices.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Saver {

    private static final String IMAGES = "/images/";
    private FileService fileService;

    public Saver(FileService fileService) {
        this.fileService = fileService;
    }

    public String saveToProject(MultipartFile image){
        File file = new File(Objects.requireNonNull(image.getOriginalFilename()));
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(image.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file.getAbsolutePath()+"    "+file.getTotalSpace();
    }

    public String checkAndSave(MultipartFile file, String question){
            if(Objects.isNull(question)){
                return saveMultipartFile(file);
            } else {
                return question;
            }
    }

    public List<String> checkAndSave(List<MultipartFile> files, List<String> variants){
        List<String> resultList = new ArrayList<>();
        if(Objects.nonNull(files)||!files.isEmpty()){
            files.forEach(f->resultList.add(saveMultipartFile(f)));
        }
        if(Objects.nonNull(variants)||!variants.isEmpty()){
            variants.forEach(v->resultList.add(v));
        }
        return resultList;
    }

    private String saveMultipartFile(MultipartFile file){

        com.diplom.models.File savedFile = fileService.save(new com.diplom.models.File(file.getOriginalFilename(), file));
        savedFile.setName(String.valueOf(savedFile.getId()));
        fileService.save(savedFile);
        return IMAGES + fileService.save(savedFile).getId();
    }
}
