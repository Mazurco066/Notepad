package com.mazurco066.notepad.model;


import java.util.ArrayList;
import java.util.List;

public class TodoList {

    //Attb
    private int id;
    private String title;
    private List<ItemList> itens;

    //Public constructors
    public TodoList() {

        this.id = 0;
        this.title = "";
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

    //Class Methods
    public void addItem(ItemList itemList) {

        if (itemList != null) this.itens.add(itemList);
    }

}
