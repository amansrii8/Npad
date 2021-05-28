package com.example.myapplication.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Notes extends RealmObject {

    @Required
    String notesID;

    @Required
    String name;

    @Required
    String data;

    public String getNotesID() {return this.notesID;}
    public String getNotesName() {return this.name;}
    public String getData() {return this.data;}

    public void setNotesID(String notesID) {this.notesID = notesID;}
    public void setNotesName(String name) {this.name = name;}
    public void setData(String data) {this.data = data;}
}
