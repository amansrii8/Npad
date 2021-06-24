package com.example.myapplication.db;

import io.realm.Realm;

public class Utility {

    private Utility() {}

    public static void insertNoteIntoDB(Notes note, Realm realmDB) {
        realmDB.beginTransaction();
        realmDB.insert(note);
        realmDB.commitTransaction();
    }

    public static void updateNoteIntoDB(Notes note, Realm realmDB) {
        realmDB.beginTransaction();
        realmDB.copyToRealmOrUpdate(note);
        realmDB.commitTransaction();
    }

}
