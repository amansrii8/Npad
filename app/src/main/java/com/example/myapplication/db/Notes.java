package com.example.myapplication.db;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Notes extends RealmObject {

    @PrimaryKey
    @Required
    private String notesID;

    private String name;

    private String data;

    private String timeOfModification;

    private boolean pin;

    public String getNotesID() {return this.notesID;}
    public String getNotesName() {return this.name;}
    public String getData() {return this.data;}
    public String getTimeOfModification() {return this.timeOfModification;}
    public boolean isPinned() { return this.pin;}

    public void setNotesID(String notesID) {this.notesID = notesID;}
    public void setNotesName(String name) {this.name = name;}
    public void setData(String data) {this.data = data;}
    public void setTimeOfModification(String timeOfModification) {this.timeOfModification = timeOfModification;}
    public void setPin(boolean isPin) {this.pin = isPin;}
}
