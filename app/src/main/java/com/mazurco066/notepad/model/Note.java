package com.mazurco066.notepad.model;

import java.util.Date;

public class Note {

    //Atributos
    private int id;
    private String title;
    private Date date;
    private String content;

    //Construtor padrão
    public Note() {

        this.title = "";
        this.content = "";
        this.date = new Date();

    }

    //Métodos getter and setter
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
