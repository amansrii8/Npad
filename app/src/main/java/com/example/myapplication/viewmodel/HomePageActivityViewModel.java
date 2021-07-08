package com.example.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.db.Notes;
import com.example.myapplication.repository.NotesRepository;

import java.util.List;

public class HomePageActivityViewModel extends ViewModel {

    private MutableLiveData<List<Object>> mNotes;
    private NotesRepository notesRepository;


    public void init() {
        if (mNotes != null) {
            return;
        }

        notesRepository = NotesRepository.getInstance();
        mNotes = notesRepository.getNotes();

    }

    public LiveData<List<Object>> getNotes() {
        return mNotes;
    }

    public void deleteNotes(String id) {
        notesRepository.deleteNote(id);
    }



}
