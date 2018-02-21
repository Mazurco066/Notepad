package com.mazurco066.notepad.model;


public class ItemList {

    //Attb
    private String task;
    private int done;

    //Public constructors
    public ItemList() {

        this.task = "";
        this.done = 0;
    }

    public ItemList(String task, int done) {

        this.task = task;
        this.done = done;
    }

    //Getter and Setters
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }
}
