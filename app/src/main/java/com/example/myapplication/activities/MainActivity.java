package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.db.Notes;

import java.util.UUID;

import io.realm.Realm;
import static com.example.myapplication.db.Utility.*;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNpad;
    private EditText editTextViewNPadTitle;
    private Realm realmDB;
    private Notes notes;
    private String noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolBar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getString(R.string.toolbar_title_note));

        editTextNpad = findViewById(R.id.edittext_npad);
        editTextViewNPadTitle = findViewById(R.id.textView_notes_title);

        Realm.init(this);
        realmDB = Realm.getDefaultInstance();

        noteID = getIntent().getStringExtra("PrimaryKey");

        if (noteID != null) {
            notes = getNotesFromPrimaryKey(noteID);
            editTextViewNPadTitle.setText(notes.getNotesName());
            editTextNpad.setText(notes.getData());
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_find:
                //TODO:

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String highlightSearchedText() {

        return "";
    }

    private Notes getNotesFromPrimaryKey(String primaryKey) {

        return realmDB
                .where(Notes.class)
                .equalTo("notesID", primaryKey).findFirst();
    }

    @Override
    public void onBackPressed() {
        Notes notes = new Notes();
        if (noteID != null) {
            updateNoteIntoDB(createNotesObject(noteID, editTextViewNPadTitle.getText().toString(),
                    editTextNpad.getText().toString(), notes), realmDB);
        } else {
            insertNoteIntoDB(createNotesObject(UUID.randomUUID().toString(),
                    editTextViewNPadTitle.getText().toString(),
                    editTextNpad.getText().toString(), notes), realmDB);
        }
        startActivity();

        super.onBackPressed();
    }

    private void startActivity() {
        Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }

    private Notes createNotesObject(String id, String title, String data, Notes notes) {
        notes.setNotesID(id);
        notes.setNotesName(title);
        notes.setData(data);
        notes.setTimeOfModification(String.valueOf(System.currentTimeMillis()));

        return notes;
    }


}