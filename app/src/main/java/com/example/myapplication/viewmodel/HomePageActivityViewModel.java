package com.example.myapplication.viewmodel;




import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.db.Notes;
import com.example.myapplication.repository.NotesRepository;

import java.util.List;

public class HomePageActivityViewModel extends ViewModel {

    private MutableLiveData<List<Notes>> mNotes;
    private NotesRepository notesRepository;



    public void init() {
        if (mNotes != null) {
            return;
        }

        notesRepository = NotesRepository.getInstance();
        mNotes = notesRepository.getNotes();

    }

    public LiveData<List<Notes>> getNotes() {
        return mNotes;
    }

}
