package com.mazurco066.notepad.model;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoList {

    //Attb
    private int id;
    private String title;
    private String date;
    private List<ItemList> itens;

    //Public constructors
    public TodoList() {

        this.id = 0;
        this.title = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy ");
        Date now = new Date();
        this.date = format.format(now);
        this.itens = new ArrayList<>();
    }

    public TodoList(int id, String title, List<ItemList> itens) {

        this.id = id;
        this.title = title;
        this.itens = itens;
    }

    //Getter/Setter
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

    public List<ItemList> getItens() {
        return itens;
    }

    public void setItens(List<ItemList> itens) {
        this.itens = itens;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
