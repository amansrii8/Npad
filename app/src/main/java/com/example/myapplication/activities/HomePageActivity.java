package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.Notes;
import com.example.myapplication.recyclerview.MyRecyclerViewAdapter;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class HomePageActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    private Button buttonStart;
    private RecyclerView recyclerViewDisplayAllNotes;
    private MyRecyclerViewAdapter adapter;
    private Realm realmDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        Toolbar myToolBar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);

        buttonStart = findViewById(R.id.button_startnotes);
        recyclerViewDisplayAllNotes = findViewById(R.id.recyclerview_display_all_files);
        Realm.init(this);
        realmDB=Realm.getDefaultInstance();

        recyclerViewDisplayAllNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this,
                getPreviouslyStoredNotes());
        recyclerViewDisplayAllNotes.setAdapter(adapter);


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(getString(R.string.note_id), getPreviouslyStoredNotes().get(position).getNotesID());
        startActivity(intent);
        finish();
    }

    private ArrayList<Notes> getPreviouslyStoredNotes() {
        RealmResults<Notes> notesData =realmDB.where(Notes.class).findAll().sort("timeOfModification", Sort.DESCENDING);
        return new ArrayList<>(notesData);
    }
}
