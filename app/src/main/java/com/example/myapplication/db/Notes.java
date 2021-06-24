package com.example.myapplication.db;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Notes extends RealmObject {

    @PrimaryKey
    @Required
    String notesID;

    String name;

    String data;

    String timeOfModification;

    public String getNotesID() {return this.notesID;}
    public String getNotesName() {return this.name;}
    public String getData() {return this.data;}
    public String getTimeOfModification() {return this.timeOfModification;}

    public void setNotesID(String notesID) {this.notesID = notesID;}
    public void setNotesName(String name) {this.name = name;}
    public void setData(String data) {this.data = data;}
    public void setTimeOfModification(String timeOfModification) {this.timeOfModification = timeOfModification;}
}
