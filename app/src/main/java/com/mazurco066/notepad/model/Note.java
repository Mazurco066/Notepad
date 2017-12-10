package com.mazurco066.notepad.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {

    //Atributos
    private int id;
    private String title;
    private String date;
    private String content;

    //Construtor padrão
    public Note() {

        this.title = "";
        this.content = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy ");
        Date now = new Date();
        this.date = format.format(now);

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
