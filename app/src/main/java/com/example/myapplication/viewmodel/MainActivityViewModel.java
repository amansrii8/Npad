package com.example.myapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.db.Notes;
import com.example.myapplication.repository.NotesRepository;

public class MainActivityViewModel extends ViewModel {

    private NotesRepository notesRepository;

    public void init() {
        notesRepository = NotesRepository.getInstance();
    }

    public void deleteNotes(String noteId) {
        notesRepository = NotesRepository.getInstance();
        notesRepository.deleteNote(noteId);
    }

    public void updateNoteIntoDB(Notes notes) {
        notesRepository = NotesRepository.getInstance();
        notesRepository.updateNoteIntoDB(notes);
    }
    public void insertNoteIntoDB(Notes notes) {
        notesRepository = NotesRepository.getInstance();
        notesRepository.insertNoteIntoDB(notes);
    }

    public String getMessage() {
        notesRepository = NotesRepository.getInstance();
        return notesRepository.getMessage();
    }
}
