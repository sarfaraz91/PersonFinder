package com.example.now.Model;

import java.util.List;

public class Interests {

    private int id;
    private String title;
    private String image;
    private String created_at;
    private String updated_at;

    public Interests(int id, String title, String image, String created_at, String updated_at) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public class MyResponse {
        public boolean status;
        public List<Interests> data;
        public String message;
    }
}


