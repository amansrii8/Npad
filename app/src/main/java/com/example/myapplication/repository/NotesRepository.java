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
    private ArrayList<Object> dataset = new ArrayList<>();

    public static NotesRepository getInstance() {
        if (instance == null) {
            instance = new NotesRepository();
        }

        return instance;
    }
    public MutableLiveData<List<Object>> getNotes() {
        setNotesData();
        MutableLiveData<List<Object>> data = new MutableLiveData<>();
        data.setValue(dataset);

        return data;
    }

    private void setNotesData() {
        realmDB = Realm.getDefaultInstance();
        RealmResults<Notes> notesData =realmDB.where(Notes.class).findAll()
                .sort("timeOfModification", Sort.DESCENDING);
        dataset = new ArrayList<>(categorizedArray(new ArrayList<>(notesData)));
    }

    private ArrayList<Object> categorizedArray(ArrayList<Notes> noteArrayObject) {
        ArrayList<Notes> notesPinned = new ArrayList<>();
        ArrayList<Notes> notesUnPinned = new ArrayList<>();
        ArrayList<Object> noteObjects = new ArrayList<>();

        for (Notes notes : noteArrayObject) {
            if (notes.isPinned()) {
                notesPinned.add(notes);
            } else {
                notesUnPinned.add(notes);
            }
        }
        if (notesPinned.size() != 0) {
            noteObjects.add("Pinned");
            noteObjects.addAll(notesPinned);
        }

        if (notesUnPinned.size() != 0) {
            noteObjects.add("Unpinned");
            noteObjects.addAll(notesUnPinned);
        }

        return noteObjects;
    }

    public void deleteNote(String notesID) {
        realmDB = Realm.getDefaultInstance();
        realmDB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Notes> results =realmDB.where(Notes.class)
                        .equalTo("notesID", notesID).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public void updateNoteIntoDB(Notes note) {
        realmDB = Realm.getDefaultInstance();
        realmDB.beginTransaction();
        realmDB.copyToRealmOrUpdate(note);
        realmDB.commitTransaction();
    }

    public void insertNoteIntoDB(Notes note) {
        realmDB = Realm.getDefaultInstance();
        realmDB.beginTransaction();
        realmDB.insert(note);
        realmDB.commitTransaction();
    }

    public String getMessage() {
        return "Empty message discarded";
    }

}
