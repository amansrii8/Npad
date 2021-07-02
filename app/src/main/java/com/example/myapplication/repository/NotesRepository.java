package com.example.myapplication.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.db.Notes;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class NotesRepository {

    private Realm realmDB;


    private static NotesRepository instance;
    private ArrayList<Notes> dataset = new ArrayList<>();

    public static NotesRepository getInstance() {
        if (instance == null) {
            instance = new NotesRepository();
        }

        return instance;
    }
    public MutableLiveData<List<Notes>> getNotes() {
        setNotesData();
        MutableLiveData<List<Notes>> data = new MutableLiveData<>();
        data.setValue(dataset);

        return data;
    }

    private void setNotesData() {
        realmDB = Realm.getDefaultInstance();
        RealmResults<Notes> notesData =realmDB.where(Notes.class).findAll()
                .sort("timeOfModification", Sort.DESCENDING);
        dataset = new ArrayList<>(notesData);
    }

}
