package com.liamk.todorealm.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ToDo extends RealmObject {
    @PrimaryKey
    private int id;

    private String title;

    public ToDo(){

    }
    public ToDo(String title){
        this.title = title;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id;}
}
