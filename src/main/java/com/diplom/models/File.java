package com.diplom.models;


import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.util.Base64;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    @Lob
    private byte[] image;

    public File() {
    }


    public File(MultipartFile image) {
        try {
            this.image = image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File(String name, MultipartFile image) {
        this.name = name;
        try {
            this.image = image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public File(long id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public String getImageAsString(){
        try {
            return new String(image, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setImage(MultipartFile image) {
        try {
            this.image = image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
