package com.restaurant.exam.data.model;

import java.io.Serializable;

public class News implements Serializable {
    private int id;
    private int idUser;
    private String img, title, link;

    public News() {
    }

    public News(int id, int idUser, String img, String title, String link) {
        this.id = id;
        this.idUser = idUser;
        this.img = img;
        this.title = title;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getLink() {
        return link;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
