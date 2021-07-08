package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.db.Notes;
import com.example.myapplication.viewmodel.MainActivityViewModel;

import java.util.UUID;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNpad;
    private EditText editTextViewNPadTitle;
    private Realm realmDB;
    private Notes notes;
    private String noteID;
    private MainActivityViewModel mainActivityViewModel;
    private Menu menu;

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

        mainActivityViewModel = ViewModelProviders.of(MainActivity.this)
                .get(MainActivityViewModel.class);

        mainActivityViewModel.init();

        noteID = getIntent().getStringExtra("PrimaryKey");

        if (noteID != null) {
            notes = getNotesFromPrimaryKey(noteID);
            editTextViewNPadTitle.setText(notes.getNotesName());
            editTextNpad.setText(notes.getData());
        } else {
            notes = new Notes();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainpage_menu, menu);
        if (noteID != null) {
            menu.getItem(0).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        this.menu = menu;
        if (notes.isPinned()) {
            menu.findItem(R.id.action_pin).setVisible(false);
            menu.findItem(R.id.action_unpin).setVisible(true);
        } else {
           menu.findItem(R.id.action_pin).setVisible(true);
           menu.findItem(R.id.action_unpin).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                mainActivityViewModel.deleteNotes(noteID);
                startActivity();
                finish();
                break;
            case R.id.action_pin:
                realmDB.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        notes.setPin(true);
                    }
                });

                menu.findItem(R.id.action_pin).setVisible(false);
                menu.findItem(R.id.action_unpin).setVisible(true);
                break;
            case R.id.action_unpin:
                realmDB.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        notes.setPin(false);
                    }
                });

                menu.findItem(R.id.action_pin).setVisible(true);
                menu.findItem(R.id.action_unpin).setVisible(false);
                break;
        }

        return super.onOptionsItemSelected(item);
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

        if (editTextViewNPadTitle.getText().toString().equals("")
                && editTextNpad.getText().toString().equals("")) {
            Toast.makeText(this, mainActivityViewModel.getMessage(), Toast.LENGTH_SHORT).show();
        } else {

            if (noteID != null) {
                mainActivityViewModel.updateNoteIntoDB(updateNotesObject(editTextViewNPadTitle.getText().toString(),
                        editTextNpad.getText().toString(), notes));
            } else {
                mainActivityViewModel.insertNoteIntoDB(createNotesObject(UUID.randomUUID().toString(),
                        editTextViewNPadTitle.getText().toString(),
                        editTextNpad.getText().toString(), notes));
            }
            startActivity();
        }

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

    private Notes updateNotesObject(String title, String data, Notes notes) {
        realmDB.beginTransaction();
        notes.setNotesName(title);
        notes.setData(data);
        notes.setTimeOfModification(String.valueOf(System.currentTimeMillis()));
        realmDB.commitTransaction();

        return notes;
    }

}