package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import com.example.myapplication.R;
import com.example.myapplication.db.Notes;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNpad;
    private TextView textViewNPad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolBar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getString(R.string.toolbar_title_note));

        editTextNpad = findViewById(R.id.edittext_npad);
        textViewNPad = findViewById(R.id.textview_npad);

        textViewNPad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                textViewNPad.setVisibility(View.INVISIBLE);
                editTextNpad.setVisibility(View.VISIBLE);
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_find:
                //TODO:

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String highlightSearchedText() {

        return "";
    }

    @Override
    public void onBackPressed() {
        Notes notes = new Notes();
        notes.setNotesID("1");
        notes.setNotesName("New Data");
        notes.setData(editTextNpad.getText().toString());

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insert(notes);
        realm.commitTransaction();


        Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
        startActivity(intent);
        finish();

        super.onBackPressed();
    }
}